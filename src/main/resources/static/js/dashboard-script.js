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
