package MemberPanelBean;
import java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.JSlider;
import javax.swing.JList;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.JSeparator;

public class MemberPanelBean extends JPanel implements Serializable {

	private String titlePanel = "Nothing", listTitle = "Nothing";
	private JTextField textField;
	private JComboBox comboBox;
	private JToggleButton tglTurnOnOff;
	private JSlider slider;
	private JSpinner spinner;
	private JLabel lblToTextfield,lblToList,lblToTogglebtn,lblToCombobox,lblToSlider,lblToSpinner;
	private JPanel panelToList;
	private JList list;
	private JSeparator separator;
	
	/**
	 * Create the panel.
	 */
	public MemberPanelBean() {
		this.setBorder(BorderFactory.createTitledBorder(titlePanel));
		setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(12, 56, 206, 22);
		add(textField);
		textField.setColumns(10);
		
		comboBox = new JComboBox();
		comboBox.setBounds(12, 120, 206, 27);
		add(comboBox);
		
		tglTurnOnOff = new JToggleButton("Turn off/on\r\n");
		tglTurnOnOff.setBounds(249, 254, 189, 33);
		add(tglTurnOnOff);
		
		slider = new JSlider();
		slider.setBounds(12, 189, 200, 23);
		add(slider);
		
		spinner = new JSpinner();
		spinner.setBounds(12, 254, 206, 33);
		add(spinner);
		
		lblToTextfield = new JLabel("Lbl to textField:");
		lblToTextfield.setBounds(12, 27, 206, 16);
		add(lblToTextfield);
		
		lblToList = new JLabel("Lbl to list:");
		lblToList.setBounds(249, 27, 189, 16);
		add(lblToList);
		
		panelToList = new JPanel();
		panelToList.setBounds(249, 56, 189, 165);
		panelToList.setBorder(BorderFactory.createTitledBorder(listTitle));
		add(panelToList);
		panelToList.setLayout(null);
		
		list = new JList();
		list.setBounds(12, 23, 165, 129);
		panelToList.add(list);
		
		lblToTogglebtn = new JLabel("Lbl to toggleBtn:");
		lblToTogglebtn.setBounds(249, 225, 189, 16);
		add(lblToTogglebtn);
		
		lblToCombobox = new JLabel("Lbl to comboBox:");
		lblToCombobox.setBounds(12, 91, 200, 16);
		add(lblToCombobox);
		
		lblToSlider = new JLabel("Lbl to slider:");
		lblToSlider.setBounds(12, 160, 206, 16);
		add(lblToSlider);
		
		lblToSpinner = new JLabel("Lbl to spinner:");
		lblToSpinner.setBounds(12, 225, 200, 16);
		add(lblToSpinner);
		
		separator = new JSeparator(SwingConstants.VERTICAL);
		separator.setBounds(236, 16, 1, 271);
		add(separator);
		
	}
	
	public String getTitlePanel() {
		return titlePanel;
	}

	public void setTitlePanel(String titlePanel) {
		this.titlePanel = titlePanel;
		this.repaint();
	}
	
	public String getListTitle() {
		return listTitle;
	}

	public void setListTitle(String listTitle) {
		this.listTitle = listTitle;
		panelToList.repaint();
	}
	
	public void setLblTextField(String titleField)
	{
		this.lblToTextfield.setText(titleField);
	}
	
	public void setLblList(String titleList)
	{
		this.lblToList.setText(titleList);
	}
	
	public void setLblToggleBtn(String titleToggle)
	{
		this.lblToTogglebtn.setText(titleToggle);
	}
	
	public void setLblComboBox(String titleCombo)
	{
		this.lblToCombobox.setText(titleCombo);
	}
	
	public void setLblSlider(String titleSlider)
	{
		this.lblToSlider.setText(titleSlider);
	}
	
	public void setLblSpinner(String titleSpinner)
	{
		this.lblToSpinner.setText(titleSpinner);
	}

}
