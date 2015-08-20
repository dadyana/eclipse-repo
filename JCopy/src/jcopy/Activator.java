package jcopy;

import java.io.File;
import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.osgi.service.datalocation.Location;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	private static final String FILE = "file";

	
    // The plug-in ID
	public static final String PLUGIN_ID = "JCopy"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	private File configurationDirectory;
    private BundleContext context;
    
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		
		this.context = context;

        // determine configuration location for this plug-in
        Location location = Platform.getConfigurationLocation();
        if (location != null) {
            URL configURL = location.getURL();
            if (configURL != null && configURL.getProtocol().startsWith(FILE)) { //$NON-NLS-1$
                configurationDirectory = new File(configURL.getFile(), PLUGIN_ID);
            }
        }
        if (configurationDirectory == null) {
            configurationDirectory = getStateLocation().toFile();
        }
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		this.context = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
