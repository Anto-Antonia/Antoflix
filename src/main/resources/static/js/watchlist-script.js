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

// script for watchlists
document.addEventListener('DOMContentLoaded', function () {
    const container = document.getElementById('watchlist-container');
    const modal = document.getElementById('watchlist-modal');
    const cancelBtn = document.getElementById('cancel-modal');
    const createBtn = document.getElementById('create-watchlist');
    const watchlistNameInput = document.getElementById('watchlist-name');

 function openModal() {
         modal.classList.remove('hidden');
         setTimeout(() => watchlistNameInput.focus(), 100);
     }

     function closeModal() {
         modal.classList.add('hidden');
         watchlistNameInput.value = '';
     }

    function renderWatchlists() {
         fetch('/api/v1/watchlist/my')
                    .then(response => {
                        if (!response.ok) throw new Error('Failed to fetch watchlists');
                        return response.json();
                    })
                    .then(watchlists => {
                        let html = '';
                        if (watchlists.length === 0) {
                            html = `
                                <div class="create-watchlist-box" id="create-watchlist-box">
                                    <i class="fa-solid fa-plus"></i>
                                    <p>Create your first Watchlist</p>
                                </div>
                            `;
                        } else {
                            watchlists.forEach(wl => {
                                html += `
                                    <div class="watchlist-box">
                                        <h3>${wl.name}</h3>
                                        <p>${wl.movies.length} movies</p>
                                    </div>
                                `;
                            });

                            html += `
                                <div class="create-watchlist-box" id="create-watchlist-box">
                                    <i class="fa-solid fa-plus"></i>
                                    <p>Create new Watchlist</p>
                                </div>
                            `;
                        }

                        container.innerHTML = html;

                        // Re-attach click listener to the new plus box
                        const plusBox = document.getElementById('create-watchlist-box');
                        if (plusBox) {
                            plusBox.addEventListener('click', openModal);
                        }
                    })
                    .catch(err => {
                        container.innerHTML = `<p class="no-results">Could not load watchlists. Try again later.</p>`;
                        console.error(err);
                    });
            }

    // Initial fetch
    renderWatchlists();

    // Cancel modal
    cancelBtn.addEventListener('click', closeModal);

    // Escape key to close modal
    document.addEventListener('keydown', (e) => {
        if (e.key === "Escape") {
            closeModal();
        }
    });

    // Create new watchlist
        createBtn.addEventListener('click', () => {
            const name = watchlistNameInput.value.trim();
            if (!name) return;

            fetch('/api/v1/watchlist/empty', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ name: name})
            })
                .then(response => {
                    if (!response.ok) throw new Error('Failed to create watchlist');
                    return response.json();
                })
                .then(() => {
                    closeModal();
                    renderWatchlists(); // Refresh UI
                })
                .catch(err => {
                    alert('Error creating watchlist');
                    console.error(err);
                });
        });
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