document.addEventListener("DOMContentLoaded", function () {
    const profileIcon = document.getElementById("profileIcon");
    const profileDropdown = document.getElementById("profileDropdown");

    profileIcon.addEventListener("click", function (event) {
        event.stopPropagation(); // Prevents closing immediately when clicking
        profileDropdown.style.display =
            profileDropdown.style.display === "block" ? "none" : "block";
    });

    // Close dropdown if clicking anywhere else
    document.addEventListener("click", function () {
        profileDropdown.style.display = "none";
    });

    // Prevent closing when clicking inside the dropdown
    profileDropdown.addEventListener("click", function (event) {
        event.stopPropagation();
    });
});


// script for searchbar
document.addEventListener('DOMContentLoaded', function () {
    const searchIcon = document.querySelector('.search-bar i');
    const searchInput = document.querySelector('#search-input');
    const searchBar = document.querySelector('.search-bar');
    const homepageBox = document.querySelector('.homepage-box');

    function performSearch(query) {
        if (!query) return;

        Promise.all([
            fetch(`/api/v1/movieSearch/title?title=${encodeURIComponent(query)}`)
                .then(res => res.ok ? res.json() : []),
            fetch(`/api/v1/seriesSearch?title=${encodeURIComponent(query)}`)
                .then(res => res.ok ? res.json() : [])
        ])
        .then(([movies, series]) => {
            if (movies.length === 0 && series.length === 0) {
                    homepageBox.innerHTML = `<p class="no-results">Whoops, we don't have what you're looking for :(</p>`;
              } else{
                displaySearchResults(movies, series);
              }
        })
        .catch(error => {
            console.error('Error:', error)
            homepageBox.innerHTML = `<p class="no-results">Whoops, something went wrong. Try again later.</p>`;
            });

    }

    function displaySearchResults(movies, series) {
        homepageBox.innerHTML = ''; // Clear previous results

        if (movies.length === 0 && series.length === 0) {
            homepageBox.innerHTML = `<p class="no-results">Whoops, we don't have what you're looking for :(</p>`;
            return;
        }

        let resultsHTML = '';

        if (movies.length > 0) {
            resultsHTML += `<h2 style="color: white;">Movies</h2><div class="result-grid">`;
            movies.forEach(movie => {
                resultsHTML += `
                    <div class="result-item">
                        <p>${movie.title}</p>
                    </div>`;
            });
            resultsHTML += `</div>`;
        }

        if (series.length > 0) {
            resultsHTML += `<h2 style="color: white;">Series</h2><div class="result-grid">`;
            series.forEach(series => {
                resultsHTML += `
                    <div class="result-item">
                        <p>${series.title}</p>
                    </div>`;
            });
            resultsHTML += `</div>`;
        }

        homepageBox.innerHTML = resultsHTML;
    }

    // Pressing "Enter" should trigger the search
    searchInput.addEventListener('keydown', function (event) { // from 'keypress' to 'keydown' -> keypress os deprecated and might not work in all browsers
        if (event.key === 'Enter') {
            event.preventDefault();
            performSearch(searchInput.value.trim());
        }
    });

    // Clicking the magnifying glass should trigger the search immediately
    searchIcon.addEventListener('click', function() {
        if (!searchBar.classList.contains('open')) {
            searchBar.classList.add('open'); // Open the search bar
            searchInput.focus();
        } else {
            performSearch(searchInput.value.trim());
        }
    });
});

// script for new added movies
document.addEventListener('DOMContentLoaded', function () {
    const newAddedLink = document.getElementById('new-added-link');
    const homepageBox = document.querySelector('.homepage-box');

    newAddedLink.addEventListener('click', function (e) {
        e.preventDefault();

        homepageBox.innerHTML = `<p class="loading">Loading new releases...</p>`;

        fetch('/api/v1/movies/recent?count=10')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to fetch new movies');
                }
                return response.json();
            })
            .then(movies => {
                displayNewlyAddedMovies(movies);
            })
            .catch(error => {
                console.error('Error:', error);
                homepageBox.innerHTML = `<p class="no-results">Error fetching new releases.</p>`;
            });
    });

    function displayNewlyAddedMovies(movies) {
        homepageBox.innerHTML = ''; // Clear previous content

        if (movies.length === 0) {
            homepageBox.innerHTML = `<p class="no-results">No new movies added.</p>`;
            return;
        }

        let resultsHTML = `<h2 style="color: white;">Newly Added Movies</h2><div class="result-grid">`;
        movies.forEach(movie => {
            resultsHTML += `
                <div class="result-item fade-in">
                    <p>${movie.title}</p>
                </div>`;
        });
        resultsHTML += `</div>`;
        homepageBox.innerHTML = resultsHTML;
    }
});

// shows/hides the main nav when scrolling on the page
let lastScrollY = window.scrollY;
const nav = document.getElementById('header');

window.addEventListener('scroll', () => {
    if (window.scrollY > lastScrollY) {
        // Scrolling down
        nav.classList.add('hide');
    } else {
        // Scrolling up
        nav.classList.remove('hide');
    }
    lastScrollY = window.scrollY;
});

// script for movie cards
function openModal(movieElement) {
    const title = movieElement.querySelector(".movie-details").getAttribute("data-title");
    const release = movieElement.querySelector(".movie-details").getAttribute("data-localDate");
    const genre = movieElement.querySelector(".movie-details").getAttribute("data-genre");
    const description = movieElement.querySelector(".movie-details").getAttribute("data-description");
    const imageUrl = movieElement.querySelector(".movie-image").src;

    // Set modal content dynamically
    document.getElementById("movie-title").innerText = title;
    document.getElementById("movie-time").innerText = release;
    document.getElementById("movie-genre").innerText = genre;
    document.getElementById("movie-description").innerText = description;
    document.getElementById("movie-image").src = imageUrl;

    // Show modal
    document.getElementById("movie-modal").style.display = "flex";
}

function closeModal() {
    document.getElementById("movie-modal").style.display = "none";
}

// Close modal when clicking outside modal content
movieModal.addEventListener('click', (e) => {
  if (!modalContent.contains(e.target)) { // Check if the click is outside modal content
    movieModal.classList.add('hidden');
  }
});
//
//// script for the movie-cards from dashboard
//// Movie modal elements
//const movieModal = document.getElementById('movie-modal');
//const closeMovieModal = document.getElementById('close-movie-modal');
//const movieTitle = document.getElementById('movie-title');
//const movieDescription = document.getElementById('movie-description');
//const movieGenre = document.getElementById('movie-genre');
//const movieTime = document.getElementById('movie-time');
//const movieImage = document.getElementById('movie-image');
//const movieModalContent = movieModal.querySelector('.modal-content');
//
//// Series modal elements
//const seriesModal = document.getElementById('series-modal');
//const closeSeriesModal = document.getElementById('close-series-modal');
//const seriesTitle = document.getElementById('series-title');
//const seriesDescription = document.getElementById('series-description');
//const seriesGenre = document.getElementById('series-genre');
//const seriesTime = document.getElementById('series-time');
//const seriesImage = document.getElementById('series-image');
//const seriesModalContent = seriesModal.querySelector('.modal-content');
//
//// Close movie modal when clicking outside content
//movieModal.addEventListener('click', (e) => {
//  if (!movieModalContent.contains(e.target)) {
//    movieModal.classList.add('hidden');
//  }
//});
//
//// Close series modal when clicking outside content
//seriesModal.addEventListener('click', (e) => {
//  if (!seriesModalContent.contains(e.target)) {
//    seriesModal.classList.add('hidden');
//  }
//});
//
//// Add interactivity to cards
//document.querySelectorAll('.movie-item').forEach(card => {
//  card.addEventListener('click', () => {
//    const type = card.dataset.type;
//    const title = card.getAttribute('data-title') || "Unknown Title";
//    const desc = card.getAttribute('data-description') || "No description.";
//    const genre = card.getAttribute('data-genre') || "N/A";
//    const time = card.getAttribute('data-time') || "No date.";
//    const image = card.getAttribute('data-image') || "images/default.png";
//
//    if (type === "movie") {
//      movieTitle.textContent = title;
//      movieDescription.textContent = desc;
//      movieTime.textContent = time;
//      movieImage.setAttribute('src', image);
//
//      // Clear and repopulate genre tags
//      movieGenre.innerHTML = '';
//      genre.split(',').forEach(g => {
//        const tag = document.createElement('span');
//        tag.classList.add('genre-tag');
//        tag.textContent = g.trim();
//        movieGenre.appendChild(tag);
//      });
//
//      movieModal.classList.remove('hidden');
//    }
//
//    if (type === "series") {
//      seriesTitle.textContent = title;
//      seriesDescription.textContent = desc;
//      seriesTime.textContent = time;
//      seriesImage.setAttribute('src', image);
//
//      // Clear and repopulate genre tags
//      seriesGenre.innerHTML = '';
//      genre.split(',').forEach(g => {
//        const tag = document.createElement('span');
//        tag.classList.add('genre-tag');
//        tag.textContent = g.trim();
//        seriesGenre.appendChild(tag);
//      });
//
//      const seasonCount = parseInt(card.getAttribute('data-seasons')) || 1;
//      const seasonSelect = document.getElementById('season-select');
//      seasonSelect.innerHTML = '';
//
//      for (let i = 1; i <= seasonCount; i++) {
//        const option = document.createElement('option');
//        option.value = i;
//        option.textContent = `Season ${i}`;
//        seasonSelect.appendChild(option);
//      }
//
//      seriesModal.classList.remove('hidden');
//    }
//  });
//});
//
//// Close modals with close buttons
//closeMovieModal.addEventListener('click', () => {
//  movieModal.classList.add('hidden');
//});
//
//closeSeriesModal.addEventListener('click', () => {
//  seriesModal.classList.add('hidden');
//});