package jcopy.preferences;

import java.util.Iterator;

import javax.swing.text.TabExpander;

import jcopy.Activator;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.internal.util.Descriptors;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the preference store that belongs to the main
 * plug-in class. That way, preferences can be accessed directly via the preference store.
 */

public class CopyPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

    private TableViewer table;
    private Composite container;
    private RepositoryInfo repositoryInfo;

    public CopyPreferencePage() throws BackingStoreException {
        IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
        setPreferenceStore(preferenceStore);
        setDescription("Caminho dos properties de cada versão");

        Preferences preferences = ConfigurationScope.INSTANCE.getNode(Activator.PLUGIN_ID);

        String destinty;
        String origen;
        String[] keys = preferences.keys();
        for (String key : keys) {
            if(!RepositoryInfo.JCOPY_PATH.equalsIgnoreCase(key)){
                 origen = preferences.get(key, "default");
                repositoryInfo.putPath(key, origen);
            }else{
                destinty = preferences.get(key, "default");
                repositoryInfo.setPath(destinty);
            }
        }

    }

    public void init(IWorkbench workbench) {
    }

    @Override
    protected Control createContents(Composite parent) {
        container = new Composite(parent, SWT.NONE);
        container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        container.setLayout(new GridLayout(2, false));

        createTable(container);
        createButtons(container);
        return null;
    }

    /**
     * Cria a tabela  que armazena os caminhos
     * @param parent
     */
    private void createTable(Composite parent) {
        Composite cmpTable = new Composite(parent, SWT.NONE);
        cmpTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
        cmpTable.setLayout(new FillLayout(SWT.HORIZONTAL));

        table = new TableViewer(cmpTable, SWT.SINGLE  | SWT.BORDER | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);
        createColumn(table.getTable(), "Nome", 100, SWT.LEFT); //$NON-NLS-1$
        createColumn(table.getTable(), "Caminho", 250, SWT.LEFT); //$NON-NLS-1$

        table.getTable().setHeaderVisible(true);
        table.getTable().setLinesVisible(true);
        table.setLabelProvider(new PathLabelProvider());
        table.setContentProvider(new TableContentProvider());
        table.setInput(RepositoryInfo.loadAll());
        
        table.getTable().addMouseListener(new MouseListener() {
            
            @Override
            public void mouseUp(MouseEvent e) {
                
            }
            
            @Override
            public void mouseDown(MouseEvent e) {
                
            }
            
            @Override
            public void mouseDoubleClick(MouseEvent e) {
                openWindow(false, false);
                
            }
        });

    }

    private void createColumn(Table tbParameters, String name, int columnWidth, int aligment) {
        TableColumn tableColumn = new TableColumn(tbParameters, aligment);
        tableColumn.setText(name);
        tableColumn.setWidth(columnWidth);
    }

    private void createCheckBoxColumn(Table tbParameters, String name, int columnWidth, int aligment) {
        TableColumn tableColumn = new TableColumn(tbParameters, aligment);
        tableColumn.setText(name);
        tableColumn.setWidth(columnWidth);
    }

    private void createButtons(Composite parent) {
        Composite cntButtons = new Composite(parent, SWT.NONE);
        cntButtons.setSize(80, 80);
        cntButtons.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, true, 1, 1));
        cntButtons.setLayout(new GridLayout(1, false));

        Button btnAdd = new Button(cntButtons, SWT.NONE);
        btnAdd.setText("Add");
        btnAdd.addMouseListener(new MouseListener() {

            @Override
            public void mouseUp(MouseEvent e) {

            }

            @Override
            public void mouseDown(MouseEvent e) {
                openWindow(false,true);
            }

            @Override
            public void mouseDoubleClick(MouseEvent e) {

            }
        });

        Button btnRemove = new Button(cntButtons, SWT.NONE);
        btnRemove.setSize(80, 80);
        btnRemove.setText("Remove");
        btnRemove.addMouseListener(new MouseListener() {

            @Override
            public void mouseUp(MouseEvent e) {

            }

            @Override
            public void mouseDown(MouseEvent e) {
                TableItem[] selection = table.getTable().getSelection();
                if (selection != null || selection.length > 0) {
                    String key = selection[0].getText();
                    repositoryInfo.getRepository().remove(key);
                    Preferences preferences = ConfigurationScope.INSTANCE.getNode(Activator.PLUGIN_ID);
                    preferences.remove(key);
                    table.setInput(RepositoryInfo.loadAll());
                }

            }

            @Override
            public void mouseDoubleClick(MouseEvent e) {
               
            }
        });
    }

    @Override
    public boolean performOk() {
        repositoryInfo.save();
        return super.performOk();
    }

    private void openWindow(boolean createNewStructure, boolean isNew) {
        JAddPath pd = null;
        if(isNew){
             pd = new JAddPath(getShell());
             pd.open();
        }else{
            TableItem[] selection = table.getTable().getSelection();
            if (selection != null || selection.length > 0) {
                String key = selection[0].getText();
                 pd = new JAddPath(getShell(),key);
                 pd.open();
                
            }
        }
        table.setInput(RepositoryInfo.loadAll());
    }

    class PathLabelProvider implements ITableLabelProvider {

        /**
         * Returns the column text
         * 
         * @param element
         *            the element
         * @param columnIndex
         *            the column index
         * @return String
         */
        public String getColumnText(Object element, int columnIndex) {
            Path path = (Path) element;
            switch (columnIndex) {
                case 0:
                    return path.getName();
                case 1:
                    return path.getPath();

            }
            return null;
        }

        @Override
        public void addListener(ILabelProviderListener listener) {
            

        }

        @Override
        public void dispose() {
            

        }

        @Override
        public boolean isLabelProperty(Object element, String property) {
            
            return false;
        }

        @Override
        public void removeListener(ILabelProviderListener listener) {
            

        }

        @Override
        public Image getColumnImage(Object element, int columnIndex) {
            
            return null;
        }

    }

    private final class TableContentProvider implements IStructuredContentProvider {

        @Override
        public Object[] getElements(Object inputElement) {
            return RepositoryInfo.loadAll().toArray();
        }

        @Override
        public void dispose() {
            

        }

        @Override
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
            

        }

    }
    
    
    
    @Override
    public void dispose() {
        container.dispose();
        super.dispose();
    }
}
