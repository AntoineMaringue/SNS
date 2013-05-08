package API;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ParseurJSON implements IParseur 
{

	private Map<String, String> informationsJson;
	
	public ParseurJSON() 
	{
		informationsJson = new HashMap<String,String>();
	}

	@Override
	public Map<String, String> getInformations(String requestHttpForGoogle) 
	{
		JSONParser parser = null;
		ContainerFactory containerFactory = null;
		Map json = null;
		Iterator iter = null;
		Map.Entry entry = null;
		
		
		parser = new JSONParser();
		containerFactory = new ContainerFactory(){
		    public List creatArrayContainer() {
		      return new LinkedList();
		    }

		    public Map createObjectContainer() {
		      return new LinkedHashMap();
		    }
		                        
		  };
		                
		  try{
		    json = (Map)parser.parse(requestHttpForGoogle, containerFactory);
		    iter = json.entrySet().iterator();
		    System.out.println("==iterate result==");
		    while(iter.hasNext()){
		      entry = (Map.Entry)iter.next();
		      System.out.println(entry.getKey() + "=>" + entry.getValue());
		      if(entry.getKey().equals("totalItems"))
		      {
		    	  if(Integer.parseInt(entry.getValue().toString())<=0)
		    	  {
		    		  return null;
		    	  }
		      }
		      else if(entry.getKey().equals("items"))
		      {
		    	 
		    	  int x = -1;
		    	  for (int i = 0; i < entry.getValue().toString().split(",").length; i++) 
		    	  {
		    		  String value = entry.getValue().toString().split(",")[i];
		    		  System.out.println(value);
		    		  if(value.contains("price") 
		    			||
		    			value.trim().contains("brand")
		    			||
		    			value.trim().contains("description")
		    			||
		    			value.trim().contains("creationTime")
		    			||
		    			value.trim().contains("modificationTime"))
		    		  {
		    			  x++;
		    			  switch (x) 
		    			  {
							case 0:
								informationsJson.put("creationTime", value.split("=")[1]);
								break;
							case 1:
								informationsJson.put("modificationTime", value.split("=")[1]);
								break;
							case 2:
								informationsJson.put("description", value.split("=")[1]);
								break;
							case 3:
								informationsJson.put("brand", value.split("=")[1]);
								break;
							case 4 :
								informationsJson.put("price", value.split("=")[1]);
								break;	
							default:
								break;
						  }
		    		  }
		    	  }
		      }
		    }
		  }
		  catch(ParseException pe)
		  {
		    System.out.println(pe);
		  }
	    
		
		return informationsJson;
	}
}
