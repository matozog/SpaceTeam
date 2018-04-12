package BeanClasses;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JSlider;
import javax.swing.JList;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import java.awt.Color;
import java.awt.SystemColor;

public class MemberPanelBean extends JPanel implements Serializable, ActionListener {

	private String titlePanel = "Nothing", listTitle = "Nothing";
	private JTextField textField,textFieldSpinnerValue;
	private JComboBox comboBox;
	private JToggleButton tglTurnOnOff;
	private JSlider slider;
	private JSpinner spinner;
	private JLabel lblToTextfield,lblToList,lblToTogglebtn,lblToCombobox,lblToSlider,lblToSpinner;
	private JPanel panelToList;
	private JList list;
	private JSeparator separator;
	private SpinnerNumberModel modelSpinner = new SpinnerNumberModel(1,1,9,1);
	private DefaultListModel modelList = new DefaultListModel<>();

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
		
		tglTurnOnOff = new JToggleButton("Turn on\r\n");
		tglTurnOnOff.setBounds(249, 254, 189, 33);
		tglTurnOnOff.addActionListener(this);
		add(tglTurnOnOff);
		
		slider = new JSlider(0,10);
		slider.setBounds(12, 189, 112, 23);
		add(slider);
		
		spinner = new JSpinner(modelSpinner);
		//((MemberPanelBean) spinner.getEditor()).getTextField().setEditable(false);
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
		
		list = new JList(modelList);
		list.setBackground(SystemColor.menu);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
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
		
		textFieldSpinnerValue = new JTextField();
		textFieldSpinnerValue.setEditable(false);
		textFieldSpinnerValue.setBackground(SystemColor.menu);
		textFieldSpinnerValue.setForeground(new Color(0, 0, 0));
		textFieldSpinnerValue.setText(Integer.toString(slider.getValue()));
		textFieldSpinnerValue.setBounds(136, 189, 82, 22);
		add(textFieldSpinnerValue);
		textFieldSpinnerValue.setColumns(10);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object z = e.getSource();
		
		if(z == tglTurnOnOff)
		{
			if(tglTurnOnOff.isSelected())
			{
				tglTurnOnOff.setText("Turn off");
			}
			else
			{
				tglTurnOnOff.setText("Turn on");
			}
		}
	}
	
	public String getTextInField()
	{
		return textField.getText();
	}
	
	public int getSelectedCombo()
	{
		return comboBox.getSelectedIndex();
	}
	
	public String getTextInSlider()
	{
		return textFieldSpinnerValue.getText();
	}
	
	public int getSpinnerValue()
	{
		return (int) modelSpinner.getValue();
	}
	
	public int getSelectedListField()
	{
		return list.getSelectedIndex();
	}
	
	public boolean isToggleSelected()
	{
		return tglTurnOnOff.isSelected();
	}
	
	public JSlider getSlider()
	{
		return slider;
	}
	
	public void setMinSpinnerValue(double min)
	{
		if((Double)modelSpinner.getValue() < min) modelSpinner.setValue(min);
		modelSpinner.setStepSize(1);
		this.modelSpinner.setMinimum(min);
	}
	
	public void setMaxSpinnerValue(double max)
	{
		if((Double)modelSpinner.getValue() > max) modelSpinner.setValue(max);
		modelSpinner.setStepSize(1);
		this.modelSpinner.setMinimum(max);
	}
	
	public void setMinSliderValue(int min)
	{
		if(slider.getValue() < min) slider.setValue(min);
		this.slider.setMinimum(min);
		this.slider.setValue(min);
	}
	
	public void setMaxSliderValue(int max)
	{
		if(slider.getValue() > max) slider.setValue(max);
		this.slider.setMaximum(max);
		this.slider.setValue(max);
	}
	
	public String getTitlePanel() {
		return titlePanel;
	}

	public void setTitlePanel(String titlePanel) {
		this.titlePanel = titlePanel;
		this.setBorder(BorderFactory.createTitledBorder(this.titlePanel));
	}
	
	public String getListTitle() {
		return listTitle;
	}

	public void setListTitle(String listTitle) {
		this.listTitle = listTitle;
		panelToList.setBorder(BorderFactory.createTitledBorder(this.listTitle));
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
	
	public JComboBox getComboBox() {
		return comboBox;
	}
	
	public DefaultListModel getModelList() {
		return modelList;
	}
	
	public JTextField getTextFieldSpinnerValue() {
		return textFieldSpinnerValue;
	}

}
