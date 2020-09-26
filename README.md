# Tinder 
## A class example: Lab of Internet Applications

### API
* **GET "/profiles/{id}"**      -- returns a given profile in a lazily fashion (no likes/proposals returned). 
                                        It has a default parameter: *mode=lazy*
* **GET "/profiles"**           -- returns all profiles in a lazily fashion (no likes returned)
                                        It has a default parameter: *mode=lazy*
* **GET "/profiles/{id}?mode=eager"**  -- returns a given profile with its likes/proposals
* **GET "/profiles"?mode=eager"**      -- returns all profiles with its likes/proposals
* **GET "/{email}/candidates"**    -- returns the candidates of user "email". A candidate is a person that matches the user's requirements (gender and passion)
* **POST "/profiles")**            -- creates a new user profile (Gson called by the framework)
* **POST "/likes"**                -- creates a list of likes/proposals for a given user (origin, list of targets). See ProfileRestController.Like inner class to see the needed parameters (json in the http call body)

#### Teaching API
Here you'll have another controller with examples with just teaching purposes. Examples that may not fit well in the Tinder
application or more importantly that may be **negative** ones.

* **POST "/teaching/profilesString"**       -- creates a new user profile (Gson called manually)
* **GET "/teaching/int/{i}**                -- returns the given int that must be less or equal to 50. Error parameter example

