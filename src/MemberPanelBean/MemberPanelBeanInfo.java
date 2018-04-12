package MemberPanelBean;



import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class MemberPanelBeanInfo extends SimpleBeanInfo{

	private PropertyDescriptor[] propertyDescriptors;
	
	public MemberPanelBeanInfo() 
	{
			try {
				PropertyDescriptor titleMemeberPanelDescriptor = new PropertyDescriptor("titlePanel", MemberPanelBean.class);
				PropertyDescriptor titleListPanelDescriptor = new PropertyDescriptor("listTitle", MemberPanelBean.class);
				
				propertyDescriptors = new PropertyDescriptor[] { titleMemeberPanelDescriptor, titleListPanelDescriptor};
			} catch (IntrospectionException e) {
				e.printStackTrace();
			}
	}
	
	public PropertyDescriptor[] getPropertyDescriptors()
	{
		return propertyDescriptors;
	}
}
