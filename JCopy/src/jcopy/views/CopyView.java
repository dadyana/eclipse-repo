package jcopy.views;


import java.net.MalformedURLException;
import java.net.URL;

import jcopy.Activator;
import jcopy.preferences.RepositoryInfo;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

import br.copy.JCopyPropertiesBranch;


/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class CopyView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "jcopy.views.CopyView";

	private TableViewer viewer;
	private Action action1;
	private Action action2;
	private Action doubleClickAction;
	private Composite container;

    private Text txtPath;
    private TableViewer table;
    
    String destiny;

    private Action addItemAction;

	/*
	 * The content provider class is responsible for
	 * providing objects to the view. It can wrap
	 * existing objects in adapters or simply return
	 * objects as-is. These objects may be sensitive
	 * to the current input of the view, or ignore
	 * it and always show the same content 
	 * (like Task List, for example).
	 */
	 
	class ViewContentProvider implements IStructuredContentProvider {
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}
		public void dispose() {
		}
		public Object[] getElements(Object parent) {
			return new String[] { "One", "Two", "Three" };
		}
	}
	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			return getText(obj);
		}
		public Image getColumnImage(Object obj, int index) {
			return getImage(obj);
		}
		public Image getImage(Object obj) {
			return PlatformUI.getWorkbench().
					getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}
		
		
	}
	class NameSorter extends ViewerSorter {
	}

	/**
	 * The constructor.
	 */
	public CopyView() {
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
	    container = new Composite(parent, SWT.NULL);
	    parent.setLayout(new RowLayout());
	    
	    
	    Preferences preferences = ConfigurationScope.INSTANCE.getNode(Activator.PLUGIN_ID);

        String[] keys;
        try {
            keys = preferences.keys();
        } catch (BackingStoreException e) {
            throw new RuntimeException(e);
        }
        
        String value = "";
        for (String key : keys) {
            if(!RepositoryInfo.JCOPY_PATH.equals(key)){
                value = preferences.get(key, "default");
                createButton(parent, key, value);
            }else{
                destiny = preferences.get(key, "default");
            }

        }
	    
	}
	
	public void createButton(final Composite parent, String name, final String path){
	    
	    Button button = new Button(parent, SWT.NULL);
        button.setText(name);
        button.addMouseListener(new MouseListener() {
            
            String bPath = path;
            
            @Override
            public void mouseUp(MouseEvent e) {
                
                
            }
            
            @Override
            public void mouseDown(MouseEvent e) {
                    try {
                        JCopyPropertiesBranch copyPropertiesBranch = new JCopyPropertiesBranch(bPath, destiny);
                    } catch (Exception e1) {
                        MessageDialog.openInformation(parent.getShell(), "JCopy", e1.getMessage());
                        throw new RuntimeException(e1);
                    }

                    MessageDialog.openInformation(parent.getShell(), "JCopy", "Arquivos copiados");
                
            }
            
            @Override
            public void mouseDoubleClick(MouseEvent e) {
                
                
            }
        });
	    
	}
	
	

	@Override
	public void dispose() {
	    
	 container.dispose();
	    super.dispose();
	}



	public void setFocus() {
	}
}