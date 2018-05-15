Project link :

https://github.com/utsav-gupta/SeattleSearch2

Clone the project and configure it on android studio. It should build by ittself with no modification.

The project follows strict MVP architecture.
The App is tested on Nexus Api 25 (Emulator), and Samsun Api 19 ( Device) and is found t be fully functional and no crashes.
The App is compiled on android sdk v 26.
All the requirements are completed as mentioned

Requirements
1. The main screen should display a search input, and should use industry best practices to perform a typeahead search against the Foursquare API.
	Complete: A standard autocomplete search box is used which remembers your last search in the session and auto suggests you accordingly

2. When a search returns results, these should be displayed in a list format. Each list item should provide, at a minimum, the name of the place (e.g., Flitch Coffee), the category of the place (e.g., Coffee Shop), the icon from the response, the distance from the center of Seattle (47.6062° N, 122.3321° W) to the place, and whether the place has been favorited by the user. Clicking a list item should launch the details screen for that place.
	Complete

3. When a search returns results, the main screen should include a Floating Action Button. Clicking the Floating Action Button should launch a full-screen map with a pin for every search result. Clicking a pin should show the name of the place on the map, and clicking on the name should then open the details screen for the given place.
	Complete.

4. The details screen for a place should use a collapsible toolbar layout to show a map in the upper half of the screen, with two pins -- one, the location of search result, and the other, the center of Seattle. The bottom half of the details screen should provide details about the place, including whether or not the place is favorited, and should include a link to the place’s website (if it exists). Clicking this link should open an external Intent to a browser installed on the device.

Complete. The url link is not available in the actual result from Foursquare Api. However the browser link functionality is implemented to check this please use the stube responses by modifying the repo file.


5.Favorite selections should be changeable, should persist across launches of the app, and should show correctly on both the main and details screens.
The user should be able to navigate between screens according to Android platform conventions.

	Complete.



6. Include instructions for building the application and any relevant documentation in a README.md file

    Complete.