# Tinder 
## A class example: Lab of Internet Applications

### API
* **@GetMapping("/profiles/{email}")**  -- returns a given profile in a lazily fashion (no likes/proposals returned)
* **@GetMapping("/profiles")**         -- returns all profiles in a lazily fashion (no likes returned)
* **@GetMapping("/fullProfiles/{email}")**  -- returns a given profile with its likes/proposals
* **@GetMapping("/fullProfiles")**          -- returns all profiles with its likes/proposals
* **@GetMapping("/{email}/candidates")**    -- returns the candidates of user "email". A candidate is a person that matches the user's requirements (gender and passion)
* **@PostMapping("/profiles")**         -- creates a new user profile (Gson called by the framework)
* **@PostMapping("/profilesString")**   -- creates a new user profile (Gson called manually)
* **@PostMapping("/likes")**            -- creates a list of likes/proposals for a given user (origin, list of targets). See ProfileRestController.Like inner class to see the needed parameters (json in the http call body)

