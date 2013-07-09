/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Sfayn
 */
@FacesValidator("controller.Datevalidator")
public class Datevalidator implements Validator{
    
    public Date date;
    @Override
    
    
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        date = (Date) value;
		if(date.getYear()>2200){ 
			FacesMessage msg = 
				new FacesMessage("", 
						"");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
 
		}
    }
}
