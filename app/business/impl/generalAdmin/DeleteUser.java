package business.impl.generalAdmin;

import javax.persistence.EntityManager;

import persistence.util.Jpa;
import models.AbstractOrganization;
import models.UploadDocs;
import models.User;
import models.exception.BusinessException;
import business.impl.Command;

public class DeleteUser implements Command{
	private User user;
	
	public DeleteUser(User newUser) {
		this.user = newUser;
	}
	@Override
	public Object execute() throws BusinessException {
		
		EntityManager em  = Jpa.getManager();
		
		user = em.merge(user);
		
		AbstractOrganization org = user.getBelongs();
		
		//borramos todas las relaciones del usuario con los documentos que subio
		//los documentos siguen perteneciendo a la ong
		for(UploadDocs u :user.getUploadDocs())
			user.removeDoc(u);
		
		
		if( org != null)
			org.removeBelongs(user);
		
		return null;
	}

}
