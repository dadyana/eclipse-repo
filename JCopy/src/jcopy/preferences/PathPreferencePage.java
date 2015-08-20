package jcopy.preferences;

import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import jcopy.Activator;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class PathPreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	private DirectoryFieldEditor editor;

    public PathPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		editor = new DirectoryFieldEditor(PreferenceConstants.P_PATH, 
				"&Directory to recieve files:", getFieldEditorParent());
        addField(editor);
	
	}

	public void init(IWorkbench workbench) {
	}
	
	@Override
	public boolean performOk() {
	    RepositoryInfo.setPath(editor.getStringValue());
	    RepositoryInfo.save();
	    return super.performOk();
	}
	
	@Override
	public void applyData(Object data) {
	    RepositoryInfo.setPath(editor.getStringValue());
	    super.applyData(data);
	}
	
}