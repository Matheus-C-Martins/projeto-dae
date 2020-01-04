package ws;

import dtos.UserDTO;
import ejbs.UserBean;
import entities.User;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/users")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class UserController {
    @EJB
    UserBean userBean;

    UserDTO toDTO(User user) {
        return new UserDTO(
                user.getUsername(),
                user.getName(),
                user.getPassword(),
                user.getEmail()
        );
    }
    List<UserDTO> toDTOs(List<User> users) {
        return users.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<UserDTO> all() {
        try {
            return toDTOs(userBean.all());
        } catch (Exception e) {
            throw new EJBException("ERRO A OBTER USERS", e);
        }
    }
}
