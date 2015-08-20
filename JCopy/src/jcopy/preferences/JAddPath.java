package jcopy.preferences;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


public class JAddPath extends Dialog {
    private Text txtName;
    private Text txtPath;
    private String name = "";
    private String path = "";
    private Button btOK;

    public JAddPath(Shell parentShell) {
        super(parentShell);
        
    }
    
    public JAddPath(Shell parentShell, String name) {
      super(parentShell);
      this.name = name;
      this.path = RepositoryInfo.getRepository().get(name).getPath();
    }

    @Override
    protected Control createDialogArea(Composite parent) {
      Composite container = (Composite) super.createDialogArea(parent);
      GridLayout layout = new GridLayout(2, false);
      layout.marginRight = 5;
      layout.marginLeft = 10;
      container.setLayout(layout);

      Label lblUser = new Label(container, SWT.NONE);
      lblUser.setText("Nome:");

      txtName = new Text(container, SWT.BORDER);
      txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,1, 1));
      txtName.setText(name);

      Label lblPassword = new Label(container, SWT.NONE);
      GridData gd_lblNewLabel = new GridData(SWT.LEFT, SWT.CENTER, false,false, 1, 1);
      gd_lblNewLabel.horizontalIndent = 1;
      lblPassword.setLayoutData(gd_lblNewLabel);
      lblPassword.setText("Caminho:");

      txtPath = new Text(container, SWT.BORDER);
      txtPath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,false, 1, 1));
      txtPath.setText(path);
      
      

      return container;
    }

    @Override
    protected void createButtonsForButtonBar(Composite parent) {
      btOK = createButton(parent, IDialogConstants.OK_ID, "OK", true);
      createButton(parent, IDialogConstants.CANCEL_ID,
          IDialogConstants.CANCEL_LABEL, false);
    }

    @Override
    protected Point getInitialSize() {
      return new Point(450, 300);
    }

    @Override
    protected void okPressed() {
      name = txtName.getText();
      path = txtPath.getText();
      if(!name.isEmpty() && !path.isEmpty()){
          RepositoryInfo.putPath(name, path);
      }
      
      super.okPressed();
    }


  } 