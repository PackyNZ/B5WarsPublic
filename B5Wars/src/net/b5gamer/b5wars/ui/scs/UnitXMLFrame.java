package net.b5gamer.b5wars.ui.scs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.SyntaxScheme;
import org.fife.ui.rsyntaxtextarea.Token;

public class UnitXMLFrame extends JFrame {

	private static final long serialVersionUID = -1092307540887570606L;

	private final RSyntaxTextArea textComponent;
	
	public UnitXMLFrame(String title, ControlSheetInterface controlSheetInterface) {
		super(title);
		Dimension size = new Dimension(1024, 768);		
		setPreferredSize(size);
		setSize(size);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel panel = new JPanel(new BorderLayout());
		
		// XML control
		this.textComponent = new RSyntaxTextArea();
		getTextComponent().setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
		getTextComponent().setCurrentLineHighlightColor(new Color(232, 242, 254));
		getTextComponent().setSelectedTextColor(new Color(137, 137, 196));
//		getTextComponent().setTextAntiAliasHint("VALUE_TEXT_ANTIALIAS_ON");
		SyntaxScheme ss = getTextComponent().getSyntaxScheme();
		ss.styles[Token.MARKUP_TAG_NAME].foreground = new Color(63, 127, 127); // element
		ss.styles[Token.MARKUP_TAG_DELIMITER].foreground = new Color(63, 127, 127); // <>
		ss.styles[Token.MARKUP_TAG_ATTRIBUTE].foreground = new Color(127, 0, 127); // attribute
		ss.styles[Token.OPERATOR].foreground = Color.black; // =
		ss.styles[Token.LITERAL_STRING_DOUBLE_QUOTE].foreground = new Color(42, 0, 255); // value
		JScrollPane scrollPane = new JScrollPane(getTextComponent());
		panel.add(scrollPane, BorderLayout.CENTER);
		
		// buttons
        if ((controlSheetInterface.getUsageMode() == UsageMode.EDIT) || (controlSheetInterface.getUsageMode() == UsageMode.ALL)) {
    		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    		panel.add(buttonPanel, BorderLayout.SOUTH);		

        	// refresh
    		JButton refreshButton = new JButton(new RefreshXMLAction(getTextComponent(), controlSheetInterface));
    		refreshButton.setSize(new Dimension(140, 25));
    		buttonPanel.add(refreshButton);		

    		// validate
    		JButton validateButton = new JButton(new ValidateXMLAction(getTextComponent()));
    		validateButton.setSize(new Dimension(140, 25));
    		buttonPanel.add(validateButton);		
    		
        	// save
    		JButton saveButton = new JButton(new SaveXMLAction(getTextComponent(), controlSheetInterface));
    		saveButton.setSize(new Dimension(140, 25));
    		buttonPanel.add(saveButton);		
        }	
        
		setContentPane(panel);
        pack();
        setVisible(true);
	}
	
	private RSyntaxTextArea getTextComponent() {
		return textComponent;
	}

	public String getXML() {
		return getTextComponent().getText();
	}

	public void setXML(final String xml) {
		getTextComponent().setText(xml);
		getTextComponent().setCaretPosition(0);
	}
	
}
