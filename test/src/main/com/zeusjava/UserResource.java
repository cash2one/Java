package com.zeusjava;

@Path("/users")
public class UserResource {
    @Context
    UriInfo uriInfo;
    /**
     * 增加用户
     * @param userId
     * @param userName
     * @param userAge
     * @param servletResponse
     * @throws IOException
     */

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void newUser(
            @FormParam("userId") String userId,
            @FormParam("userName") String userName,
            @FormParam("userAge") int userAge,
            @Context HttpServletResponse servletResponse
    ) throws IOException {
        User user = new User(userId,userName,userAge);
        UserCache.getUserCache().put(userId,user);
        URI uri = uriInfo.getAbsolutePathBuilder().path(userId).build();
        Response.created(uri).build();
    }
}