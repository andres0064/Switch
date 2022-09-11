package com.example.demo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

public class GetListFilesProcess {
	
	public long sumaProcesoActual=0;
	public int nblock=0;
	public Map<Integer,Block> mapBlock = new HashMap<Integer,Block>();
	
	public void ListaReglas() {
		
		
		
		
		Map<Integer,PACProcess> mapPac = new HashMap<Integer,PACProcess>();
		
		List<MetaDataFile> lista = new ArrayList<MetaDataFile>();
		
		File micarpeta= new File("C:/springDev/BANCAPP/OUT/CCB2");
		 
        File[] listaFicheros=micarpeta.listFiles();
        
        sumaProcesoActual=0;
        nblock=0;
        mapBlock = new HashMap<Integer,Block>();
        for (int i=0;i<=listaFicheros.length-1;i++) {
 
                String nomFilePath = listaFicheros[i].getAbsolutePath();
                String nomFile = listaFicheros[i].getName();
                
                if(nomFile.indexOf("_CC_")>0) {
                	//System.out.println("encontro el archivo de cifras");
                	nblock++;
                	
                	Path path = Paths.get(nomFilePath);
                	try {
						Stream<String> stream = Files.lines(path);
						stream.forEach((s)->{
							if(s!=null && !s.isEmpty()){
								
								//System.out.println(s);
								String[] datos = s.split(":");
								long nReg = Long.parseLong(datos[1].trim());
								//System.out.println("numero de registros: "+nReg);
								sumaProcesoActual=sumaProcesoActual+nReg;
								lista.add(new MetaDataFile(nomFile,nomFilePath,nReg,"Producto"));
								mapBlock.put(nblock, new Block(nblock,path.getFileName().toString(),nReg,0,"none"));
							}
						});
						
						int listaTam= lista.size();
						long acumula =0;
						for(int x=0; x<=listaTam-1;x++) {
							//System.out.println(lista.get(x).getRutaArchivo()+"--"+lista.get(x).getNombreArchivo()+"--"+lista.get(x).getNumReg()+"--"+lista.get(x).getProducto());
						}
						
						
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                	
                } //if de archivos cifras
         
        } // for scan de directorio y archivos
        
        System.out.println("key|ID|ARCHIVO|SIZE|ASIGNADO|PAC");
		Iterator it11 = mapBlock.keySet().iterator();
		 while(it11.hasNext()){
			Integer key11 = (Integer) it11.next();
			Block bloque = mapBlock.get(key11);
			System.out.println(key11 + "|" + bloque.getId()+"|"+bloque.getNomFile()+"|"+bloque.getVolumen()+"|"+bloque.getAsignado()+"|"+bloque.getPac());
		 }
        
        System.out.println(" Total de registros "+ sumaProcesoActual);
        
        Long l = (Long)sumaProcesoActual;
		double pac1 = l.doubleValue();
		double pacPor1 = pac1*Double.parseDouble("60")/100.00;
		//System.out.println(pacPor);
		
		double pac2 = l.doubleValue();
		double pacPor2 = pac2*Double.parseDouble("30")/100.00;
		
		double pac3 = l.doubleValue();
		double pacPor3 = pac3*Double.parseDouble("10")/100.00;
		
		
		//System.out.println("PAC1 "+pacPor1+" PAC2 "+pacPor2+" Pac3 "+pacPor3);
		
		mapPac.put(1, new PACProcess(1,"PAC1",6000,pacPor1,0));
		mapPac.put(2, new PACProcess(2,"PAC2",3000,pacPor2,0));
		mapPac.put(3, new PACProcess(3,"PAC3",1000,pacPor3,0));
		
		System.out.println("id|PAC|PROD|PROCESO|ACUMULADO|");
		 Iterator it9 = mapPac.keySet().iterator();
		 while(it9.hasNext()){
			Integer key9 = (Integer) it9.next();
			PACProcess x2 = mapPac.get(key9);
			System.out.println(key9+"|"+x2.getId()+"|"+x2.getNombre()+"|"+x2.getTotalProd()+"|"+x2.getTotalProcess()+"|"+x2.getSumaProcess());
		 }
		
		
		
		Iterator it = mapBlock.keySet().iterator();
		while(it.hasNext()){
		  Integer key = (Integer) it.next();
		  Block bloque = mapBlock.get(key);
		  //System.out.println(key + " -> id: " + bloque.getId()+" size: "+bloque.getVolumen()+" Asigando: "+bloque.getAsignado()+" PAC Asignado: "+bloque.getPac());
		    Iterator it2 = mapPac.keySet().iterator();
			while(it2.hasNext()){
				Integer key2 = (Integer) it2.next();
				PACProcess pacProceso = mapPac.get(key2);
				///Regla
				///System.out.println("Nombre Pac: "+pacProceso.getNombre()+" Acumulado de Asignacion: "+pacProceso.getSumaProcess()+" Total Proceso: "+pacProceso.getTotalProcess()+" Total Produccion: "+pacProceso.getTotalProd());
				Long lu = (Long)bloque.getVolumen();
				double tamBloque = lu.doubleValue()+pacProceso.getSumaProcess();
				//System.out.println(pacProceso.getSumaProcess()+" >>>>>>>>>>>>> "+ tamBloque);
				if( tamBloque <= pacProceso.getTotalProcess() && bloque.getAsignado() == 0) {
					double acum = pacProceso.getSumaProcess()+Double.parseDouble(String.valueOf(bloque.getVolumen())+".00");
					//System.out.println("<<<<<<<<<<<<<<<<"+acum);
					pacProceso.setSumaProcess(pacProceso.getSumaProcess()+Double.parseDouble(String.valueOf(bloque.getVolumen())+".00"));
				
					 mapPac.replace(key2, pacProceso);
					 pacProceso = mapPac.get(key2);
					 
					 bloque.setAsignado(1);
					 bloque.setPac(pacProceso.getNombre());
					 mapBlock.replace(key, bloque);
					 
					 break;
				
				}/// if tamaños de bloque
			 }////while mapPac
			
			bloque = mapBlock.get(key);
			//System.out.println("key: " + key + " -> Valor: " + bloque.getId()+" size: "+bloque.getVolumen()+" Asigando: "+bloque.getAsignado()+" PAC Asignado: "+bloque.getPac());
			
		} /// while de mapBlock
		
		
		//////////////// si hay bloques sin asignar se vuelve a aplicar reglas
		
		Map<Integer, Double> tre= new HashMap<Integer, Double>();
		
	  	Iterator it5 = mapPac.keySet().iterator();
		 while(it5.hasNext()){
			Integer key5 = (Integer) it5.next();
			PACProcess pacProceso = mapPac.get(key5);
			//System.out.println(key5+"|"+pacProceso.getNombre()+"|"+pacProceso.getTotalProd()+"|"+pacProceso.getTotalProcess()+"|"+pacProceso.getSumaProcess());
			double dife = pacProceso.getTotalProcess()-pacProceso.getSumaProcess();
			//System.out.println(dife);
			tre.put(key5, dife);
		 }
		
		
		 //System.out.println(tre);
		 
		 LinkedHashMap<Integer, Double> reverseSortedMap = new LinkedHashMap<>();
		 
		//Use Comparator.reverseOrder() for reverse ordering
		tre.entrySet()
		  .stream()
		  .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) 
		  .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
		 
		
		Map<Integer,PACProcess> mapTmp = new HashMap<Integer,PACProcess>();
		//System.out.println("Reverse Sorted Map   : " + reverseSortedMap);
		 int w=1;
		 Iterator it6 = reverseSortedMap.keySet().iterator();
		 while(it6.hasNext()){
			Integer key6 = (Integer) it6.next();
			double x1 = tre.get(key6);
			//System.out.println(key6+" --- "+x1);
			PACProcess pacProceso = mapPac.get(key6);
			mapTmp.put(w, pacProceso);
			w++;
		 }
     	
		//System.out.println(mapTmp);
		
		Iterator it7 = mapTmp.keySet().iterator();
		 while(it7.hasNext()){
			Integer key7 = (Integer) it7.next();
			PACProcess x2 = mapTmp.get(key7);
			//System.out.println(key7+" --- "+x2.getId()+" "+x2.getNombre()+" "+x2.getSumaProcess());
		 }
		int noExiste=0;
		List<Integer> quePac = new ArrayList<Integer>();
		Iterator it3 = mapBlock.keySet().iterator();
		while(it3.hasNext()){
		  Integer key3 = (Integer) it3.next();
		  Block bloque = mapBlock.get(key3);
		  //System.out.println(key + " -> id: " + bloque.getId()+" size: "+bloque.getVolumen()+" Asigando: "+bloque.getAsignado()+" PAC Asignado: "+bloque.getPac());
		    
		  	Iterator it4 = mapTmp.keySet().iterator();
			while(it4.hasNext()){
				Integer key4 = (Integer) it4.next();
				PACProcess pacProceso = mapTmp.get(key4);
				noExiste=0;
				if(quePac!=null) {
					for(int z=0;z<=quePac.size()-1;z++) {
						if(quePac.get(z)==key4) {
							noExiste=1;
							break;
						}
					}
				}
				
				
				///Regla
				///System.out.println("Nombre Pac: "+pacProceso.getNombre()+" Acumulado de Asignacion: "+pacProceso.getSumaProcess()+" Total Proceso: "+pacProceso.getTotalProcess()+" Total Produccion: "+pacProceso.getTotalProd());
				if(noExiste==0) {
				Long lu = (Long)bloque.getVolumen();
				double tamBloque = lu.doubleValue()+pacProceso.getSumaProcess();
				//System.out.println(pacProceso.getSumaProcess()+" >>>>>>>>>>>>> "+ tamBloque);
				if( (tamBloque >= pacProceso.getTotalProcess() && tamBloque <= pacProceso.getTotalProd()) && bloque.getAsignado() == 0) {
					double acum = pacProceso.getSumaProcess()+Double.parseDouble(String.valueOf(bloque.getVolumen())+".00");
					//System.out.println("<<<<<<<<<<<<<<<<"+acum);
					pacProceso.setSumaProcess(pacProceso.getSumaProcess()+Double.parseDouble(String.valueOf(bloque.getVolumen())+".00"));
				
					 mapPac.replace(pacProceso.getId(), pacProceso);
					 pacProceso = mapPac.get(pacProceso.getId());
					 
					 bloque.setAsignado(1);
					 bloque.setPac(pacProceso.getNombre());
					 mapBlock.replace(key3, bloque);
					 quePac.add(key4);
					 break;
				
				}/// if tamaños de bloque
				
				
			 }////while mapTmp
			
			bloque = mapBlock.get(key3);
			//System.out.println("key: " + key3 + " -> Valor: " + bloque.getId()+" size: "+bloque.getVolumen()+" Asigando: "+bloque.getAsignado()+" PAC Asignado: "+bloque.getPac());
			} // if no Existe
		} /// while de mapBlock
		System.out.println("key|ID|ARCHIVO|SIZE|ASIGNADO|PAC");
		Iterator it10 = mapBlock.keySet().iterator();
		 while(it10.hasNext()){
			Integer key10 = (Integer) it10.next();
			Block bloque = mapBlock.get(key10);
			System.out.println(key10 + "|" + bloque.getId()+"|"+bloque.getNomFile()+"|"+bloque.getVolumen()+"|"+bloque.getAsignado()+"|"+bloque.getPac());
		 }
		 System.out.println("id|PAC|PROD|PROCESO|ACUMULADO");
		Iterator it8 = mapPac.keySet().iterator();
		 while(it8.hasNext()){
			Integer key8 = (Integer) it8.next();
			PACProcess x2 = mapPac.get(key8);
			System.out.println(key8+"|"+x2.getId()+"|"+x2.getNombre()+"|"+x2.getTotalProd()+"|"+x2.getTotalProcess()+"|"+x2.getSumaProcess());
		 }
		
		
	}
	
public void ListaReglas02() {
		
		
		
		
		Map<Integer,PACProcess> mapPac = new HashMap<Integer,PACProcess>();
		
		List<MetaDataFile> lista = new ArrayList<MetaDataFile>();
		
		File micarpeta= new File("C:/springDev/BANCAPP/OUT/CCB2");
		 
        File[] listaFicheros=micarpeta.listFiles();
        
        sumaProcesoActual=0;
        nblock=0;
        mapBlock = new HashMap<Integer,Block>();
        for (int i=0;i<=listaFicheros.length-1;i++) {
 
                String nomFilePath = listaFicheros[i].getAbsolutePath();
                String nomFile = listaFicheros[i].getName();
                
                if(nomFile.indexOf("_CC_")>0) {
                	//System.out.println("encontro el archivo de cifras");
                	nblock++;
                	
                	Path path = Paths.get(nomFilePath);
                	try {
						Stream<String> stream = Files.lines(path);
						stream.forEach((s)->{
							if(s!=null && !s.isEmpty()){
								
								//System.out.println(s);
								String[] datos = s.split(":");
								long nReg = Long.parseLong(datos[1].trim());
								//System.out.println("numero de registros: "+nReg);
								sumaProcesoActual=sumaProcesoActual+nReg;
								lista.add(new MetaDataFile(nomFile,nomFilePath,nReg,"Producto"));
								mapBlock.put(nblock, new Block(nblock,path.getFileName().toString(),nReg,0,"none"));
							}
						});
						
						int listaTam= lista.size();
						long acumula =0;
						for(int x=0; x<=listaTam-1;x++) {
							//System.out.println(lista.get(x).getRutaArchivo()+"--"+lista.get(x).getNombreArchivo()+"--"+lista.get(x).getNumReg()+"--"+lista.get(x).getProducto());
						}
						
						
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                	
                } //if de archivos cifras
         
        } // for scan de directorio y archivos
        
        System.out.println("key|ID|ARCHIVO|SIZE|ASIGNADO|PAC");
		Iterator it11 = mapBlock.keySet().iterator();
		 while(it11.hasNext()){
			Integer key11 = (Integer) it11.next();
			Block bloque = mapBlock.get(key11);
			System.out.println(key11 + "|" + bloque.getId()+"|"+bloque.getNomFile()+"|"+bloque.getVolumen()+"|"+bloque.getAsignado()+"|"+bloque.getPac());
		 }
        
        System.out.println(" Total de registros "+ sumaProcesoActual);
        
        Long l = (Long)sumaProcesoActual;
		double pac1 = l.doubleValue();
		double pacPor1 = pac1*Double.parseDouble("60")/100.00;
		//System.out.println(pacPor);
		
		double pac2 = l.doubleValue();
		double pacPor2 = pac2*Double.parseDouble("30")/100.00;
		
		double pac3 = l.doubleValue();
		double pacPor3 = pac3*Double.parseDouble("10")/100.00;
		
		
		//System.out.println("PAC1 "+pacPor1+" PAC2 "+pacPor2+" Pac3 "+pacPor3);
		
		mapPac.put(1, new PACProcess(1,"PAC1",6000,pacPor1,0));
		mapPac.put(2, new PACProcess(2,"PAC2",3000,pacPor2,0));
		mapPac.put(3, new PACProcess(3,"PAC3",1000,pacPor3,0));
		
		System.out.println("id|PAC|PROD|PROCESO|ACUMULADO|");
		 Iterator it9 = mapPac.keySet().iterator();
		 while(it9.hasNext()){
			Integer key9 = (Integer) it9.next();
			PACProcess x2 = mapPac.get(key9);
			System.out.println(key9+"|"+x2.getId()+"|"+x2.getNombre()+"|"+x2.getTotalProd()+"|"+x2.getTotalProcess()+"|"+x2.getSumaProcess());
		 }
		
		 int noExiste1=0;
	     List<Integer> quePac1 = new ArrayList<Integer>();
		
		Iterator it = mapBlock.keySet().iterator();
		while(it.hasNext()){
		  Integer key = (Integer) it.next();
		  Block bloque = mapBlock.get(key);
		  //System.out.println(key + " -> id: " + bloque.getId()+" size: "+bloque.getVolumen()+" Asigando: "+bloque.getAsignado()+" PAC Asignado: "+bloque.getPac());
		    Iterator it2 = mapPac.keySet().iterator();
			while(it2.hasNext()){
				Integer key2 = (Integer) it2.next();
				PACProcess pacProceso = mapPac.get(key2);
				///////
				noExiste1=0;
				if(quePac1!=null) {
					for(int z=0;z<=quePac1.size()-1;z++) {
						if(quePac1.get(z)==key2) {
							noExiste1=1;
							break;
						}
					}
				}
				///Regla
				///System.out.println("Nombre Pac: "+pacProceso.getNombre()+" Acumulado de Asignacion: "+pacProceso.getSumaProcess()+" Total Proceso: "+pacProceso.getTotalProcess()+" Total Produccion: "+pacProceso.getTotalProd());
				Long lu = (Long)bloque.getVolumen();
				double tamBloque = lu.doubleValue()+pacProceso.getSumaProcess();
				//System.out.println(pacProceso.getSumaProcess()+" >>>>>>>>>>>>> "+ tamBloque);
				if( tamBloque <= pacProceso.getTotalProcess() && bloque.getAsignado() == 0) {
					double acum = pacProceso.getSumaProcess()+Double.parseDouble(String.valueOf(bloque.getVolumen())+".00");
					//System.out.println("<<<<<<<<<<<<<<<<"+acum);
					pacProceso.setSumaProcess(pacProceso.getSumaProcess()+Double.parseDouble(String.valueOf(bloque.getVolumen())+".00"));
				
					 mapPac.replace(key2, pacProceso);
					 pacProceso = mapPac.get(key2);
					 
					 bloque.setAsignado(1);
					 bloque.setPac(pacProceso.getNombre());
					 mapBlock.replace(key, bloque);
					 
					 break;
				
				}/// if tamaños de bloque
			 }////while mapPac
			
			bloque = mapBlock.get(key);
			//System.out.println("key: " + key + " -> Valor: " + bloque.getId()+" size: "+bloque.getVolumen()+" Asigando: "+bloque.getAsignado()+" PAC Asignado: "+bloque.getPac());
			
		} /// while de mapBlock
		
		
		//////////////// si hay bloques sin asignar se vuelve a aplicar reglas
		
		Map<Integer, Double> tre= new HashMap<Integer, Double>();
		
	  	Iterator it5 = mapPac.keySet().iterator();
		 while(it5.hasNext()){
			Integer key5 = (Integer) it5.next();
			PACProcess pacProceso = mapPac.get(key5);
			//System.out.println(key5+"|"+pacProceso.getNombre()+"|"+pacProceso.getTotalProd()+"|"+pacProceso.getTotalProcess()+"|"+pacProceso.getSumaProcess());
			double dife = pacProceso.getTotalProcess()-pacProceso.getSumaProcess();
			//System.out.println(dife);
			tre.put(key5, dife);
		 }
		
		
		 //System.out.println(tre);
		 
		 LinkedHashMap<Integer, Double> reverseSortedMap = new LinkedHashMap<>();
		 
		//Use Comparator.reverseOrder() for reverse ordering
		tre.entrySet()
		  .stream()
		  .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) 
		  .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
		 
		
		Map<Integer,PACProcess> mapTmp = new HashMap<Integer,PACProcess>();
		//System.out.println("Reverse Sorted Map   : " + reverseSortedMap);
		 int w=1;
		 Iterator it6 = reverseSortedMap.keySet().iterator();
		 while(it6.hasNext()){
			Integer key6 = (Integer) it6.next();
			double x1 = tre.get(key6);
			//System.out.println(key6+" --- "+x1);
			PACProcess pacProceso = mapPac.get(key6);
			mapTmp.put(w, pacProceso);
			w++;
		 }
     	
		//System.out.println(mapTmp);
		
		Iterator it7 = mapTmp.keySet().iterator();
		 while(it7.hasNext()){
			Integer key7 = (Integer) it7.next();
			PACProcess x2 = mapTmp.get(key7);
			//System.out.println(key7+" --- "+x2.getId()+" "+x2.getNombre()+" "+x2.getSumaProcess());
		 }
		int noExiste=0;
		List<Integer> quePac = new ArrayList<Integer>();
		Iterator it3 = mapBlock.keySet().iterator();
		while(it3.hasNext()){
		  Integer key3 = (Integer) it3.next();
		  Block bloque = mapBlock.get(key3);
		  //System.out.println(key + " -> id: " + bloque.getId()+" size: "+bloque.getVolumen()+" Asigando: "+bloque.getAsignado()+" PAC Asignado: "+bloque.getPac());
		    
		  	Iterator it4 = mapTmp.keySet().iterator();
			while(it4.hasNext()){
				Integer key4 = (Integer) it4.next();
				PACProcess pacProceso = mapTmp.get(key4);
				noExiste=0;
				if(quePac!=null) {
					for(int z=0;z<=quePac.size()-1;z++) {
						if(quePac.get(z)==key4) {
							noExiste=1;
							break;
						}
					}
				}
				
				
				///Regla
				///System.out.println("Nombre Pac: "+pacProceso.getNombre()+" Acumulado de Asignacion: "+pacProceso.getSumaProcess()+" Total Proceso: "+pacProceso.getTotalProcess()+" Total Produccion: "+pacProceso.getTotalProd());
				if(noExiste==0) {
				Long lu = (Long)bloque.getVolumen();
				double tamBloque = lu.doubleValue()+pacProceso.getSumaProcess();
				//System.out.println(pacProceso.getSumaProcess()+" >>>>>>>>>>>>> "+ tamBloque);
				if( (tamBloque >= pacProceso.getTotalProcess() && tamBloque <= pacProceso.getTotalProd()) && bloque.getAsignado() == 0) {
					double acum = pacProceso.getSumaProcess()+Double.parseDouble(String.valueOf(bloque.getVolumen())+".00");
					//System.out.println("<<<<<<<<<<<<<<<<"+acum);
					pacProceso.setSumaProcess(pacProceso.getSumaProcess()+Double.parseDouble(String.valueOf(bloque.getVolumen())+".00"));
				
					 mapPac.replace(pacProceso.getId(), pacProceso);
					 pacProceso = mapPac.get(pacProceso.getId());
					 
					 bloque.setAsignado(1);
					 bloque.setPac(pacProceso.getNombre());
					 mapBlock.replace(key3, bloque);
					 quePac.add(key4);
					 break;
				
				}/// if tamaños de bloque
				
				
			 }////while mapTmp
			
			bloque = mapBlock.get(key3);
			//System.out.println("key: " + key3 + " -> Valor: " + bloque.getId()+" size: "+bloque.getVolumen()+" Asigando: "+bloque.getAsignado()+" PAC Asignado: "+bloque.getPac());
			} // if no Existe
		} /// while de mapBlock
		System.out.println("key|ID|ARCHIVO|SIZE|ASIGNADO|PAC");
		Iterator it10 = mapBlock.keySet().iterator();
		 while(it10.hasNext()){
			Integer key10 = (Integer) it10.next();
			Block bloque = mapBlock.get(key10);
			System.out.println(key10 + "|" + bloque.getId()+"|"+bloque.getNomFile()+"|"+bloque.getVolumen()+"|"+bloque.getAsignado()+"|"+bloque.getPac());
		 }
		 System.out.println("id|PAC|PROD|PROCESO|ACUMULADO");
		Iterator it8 = mapPac.keySet().iterator();
		 while(it8.hasNext()){
			Integer key8 = (Integer) it8.next();
			PACProcess x2 = mapPac.get(key8);
			System.out.println(key8+"|"+x2.getId()+"|"+x2.getNombre()+"|"+x2.getTotalProd()+"|"+x2.getTotalProcess()+"|"+x2.getSumaProcess());
		 }
		
		
	}

	private void forEach(Object object) {
		// TODO Auto-generated method stub
		
	}

}
