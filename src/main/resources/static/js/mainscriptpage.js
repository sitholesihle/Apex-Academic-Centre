	/* NAVBAR AND IMAGES */
	
	const navbarToggle = document.getElementById('navbar-toggle');
	const navLinks = document.querySelector('.nav-links');

	// Toggle menu on button click
	navbarToggle.addEventListener('click', function() {
	    navLinks.classList.toggle('show');
	});

	// Close menu when a link is clicked
	navLinks.querySelectorAll('li a').forEach(link => {
	    link.addEventListener('click', function() {
	        navLinks.classList.remove('show'); // Close the menu
	    });
	});

	// Optional: Close menu when clicking outside of it
	window.addEventListener('click', function(event) {
	    if (!navbarToggle.contains(event.target) && !navLinks.contains(event.target)) {
	        navLinks.classList.remove('show'); // Close the menu
	    }
	});


	document.addEventListener("DOMContentLoaded", function() {
		
	  var imageWrapper = document.querySelector('.image-wrapper');
	  var tutorContainer = document.querySelector('.tutor-container');
	  var tutorRect = tutorContainer.getBoundingClientRect();
	  var tutorTop = tutorRect.top + window.scrollY;
	  var images = document.querySelectorAll('.image');

	  window.addEventListener('scroll', function() {
	    var scrollTop = window.scrollY;

	    // Check if the scroll position is past the tutor container
	    if (scrollTop > tutorTop) {
	      images.forEach(function(image) {
	        image.classList.add('fixed-image');
	      });
	    } else {
	      images.forEach(function(image) {
	        image.classList.remove('fixed-image');
	      });
	    }
	  });
	});
	
	/* NAVBAR AND IMAGES */
	
	/* SUSCRIBE */
	
	function subscribeMethod() {
	    var emailField = document.querySelector('input[name="email"]');

	    
	    if (!emailField.checkValidity()) {
	       
	        alert("Please enter a valid email address.");
	        return false; 
	    }

	    
	    alert("Thank you for subscribing, please check your emails.");
	    return true; 
	}
	
	/* SUSCRIBE */
	
	/*SEARCH ENGINE*/
	       function checkInputs() {
	            // Get the input values by their IDs
	            const location = document.getElementById('eng-location').value;
	            const subject = document.getElementById('eng-subject').value;
	            const curriculum = document.getElementById('eng-curriculum').value;
	            const tutoring = document.getElementById('eng-tutoring').value;

	            // Initialize an array to hold the values
	            const values = [location, subject, curriculum, tutoring];

	                // Initialize an array to hold prefixed values
	                const prefixedValues = [];

	                // Add prefixes based on non-empty values
	                if (location !== "") {
	                    prefixedValues.push("l" + location);
	                }
	                if (subject !== "") {
	                    prefixedValues.push("s" + subject);
	                }
	                if (curriculum !== "") {
	                    prefixedValues.push("c" + curriculum);
	                }
	                if (tutoring !== "") {
	                    prefixedValues.push("t" + tutoring);
	                }


	            const nonEmptyValues = prefixedValues.filter(value => value !== ""); // Filter out empty values

	            // Alert the values if there are any non-empty inputs
	            if (nonEmptyValues.length > 0) {

	                var searchInput =  nonEmptyValues.join(', ');
	              
	                const dataToSend = {
	    search: searchInput // Assuming searchInput is formatted correctly
	};

	// Fetch using POST method
	fetch('/searchEngine', {
	    method: 'POST',
	    headers: {
	        'Content-Type': 'application/json'
	    },
	    body: JSON.stringify(dataToSend) // Send the data as JSON
	})
	.then(response => {
	    if (!response.ok) {
	        throw new Error('Network response was not ok');
	    }
	    return response.text(); // or response.json() if you're expecting JSON
	})
	.then(result => {
	    // Handle success here
	    console.log('Search results received:', result);

	    window.location.href = '/searchOptimazation';

	})
	.catch(error => {
	    console.error('Error during search:', error);
	    // Assuming messageElement is defined in your scope
	    messageElement.innerText = 'An error occurred while trying to search'; // Show error message
	});

	            } else {
	                alert("One of the fields must have a value");
	            }
	        }

	        // Function to serialize data to query parameters
	function serialize(obj) {
	    return Object.keys(obj)
	        .map(key => encodeURIComponent(key) + '=' + encodeURIComponent(obj[key]))
	        .join('&');
	}
	/*SEARCH ENGINE*/
	
	