package jcopy.preferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jcopy.Activator;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

/**Armaze os caminhos em tempo de execussão.
 * 
 * @author Dayana.Trapp
 */
public class RepositoryInfo {

     public static final String JCOPY_PATH = "JcopyPath";
    static Map<String, Path>  repository = new HashMap<String, Path>();
     static String path = "";

    /**
     * Adiciona o caminho  
     * @param name
     * @param path
     */
    public static void putPath(String name, String path) {
        if (!repository.containsKey(name)) {
            Path p = new Path(name, path);
            repository.put(name, p);
        } else {
            Path path2 = repository.get(name);
            path2.setPath(path);
        }
    }

    
    public static String getPath() {
        return path;
    }

    
    public static void setPath(String path) {
        RepositoryInfo.path = path;
    }

    public static Map<String, Path> getRepository() {
        return repository;
    }

    public static List<Path> loadAll() {
        List<Path> list = Collections.emptyList();
        list = new ArrayList<Path>();
        for (Iterator<Path> iterator = repository.values().iterator(); iterator.hasNext();) {
            Path path = iterator.next();
            list.add(path);
        }

        return list;
    }
    
    public static void save(){
        Preferences preferences = ConfigurationScope.INSTANCE.getNode(Activator.PLUGIN_ID);
        for (Iterator<String> iterator = getRepository().keySet().iterator(); iterator.hasNext();) {
            String key = iterator.next();
            preferences.put(key, getRepository().get(key).getPath());
        }
        
       preferences.put(JCOPY_PATH, path);

        try {
            preferences.flush();
        } catch (BackingStoreException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }

    }
}

