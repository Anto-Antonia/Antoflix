// script for interactive movie cards
// Movie modal elements
const movieModal = document.getElementById('movie-modal');
const closeMovieModal = document.getElementById('close-movie-modal');
const movieTitle = document.getElementById('movie-title');
const movieDescription = document.getElementById('movie-description');
const movieGenre = document.getElementById('movie-genre');
const movieTime = document.getElementById('movie-time');
const movieImage = document.getElementById('movie-image');
const movieModalContent = movieModal.querySelector('.modal-content');

// Series modal elements
const seriesModal = document.getElementById('series-modal');
const closeSeriesModal = document.getElementById('close-series-modal');
const seriesTitle = document.getElementById('series-title');
const seriesDescription = document.getElementById('series-description');
const seriesGenre = document.getElementById('series-genre');
const seriesTime = document.getElementById('series-time');
const seriesImage = document.getElementById('series-image');
const seriesModalContent = seriesModal.querySelector('.modal-content');

// Close movie modal when clicking outside content
movieModal.addEventListener('click', (e) => {
  if (!movieModalContent.contains(e.target)) {
    movieModal.classList.add('hidden');
  }
});

// Close series modal when clicking outside content
seriesModal.addEventListener('click', (e) => {
  if (!seriesModalContent.contains(e.target)) {
    seriesModal.classList.add('hidden');
  }
});

// Add interactivity to cards
document.querySelectorAll('.movie-item').forEach(card => {
  card.addEventListener('click', () => {
    const type = card.dataset.type;
    const title = card.getAttribute('data-title') || "Unknown Title";
    const desc = card.getAttribute('data-description') || "No description.";
    const genre = card.getAttribute('data-genre') || "N/A";
    const time = card.getAttribute('data-time') || "No date.";
    const image = card.getAttribute('data-image') || "images/default.png";

    if (type === "movie") {
      movieTitle.textContent = title;
      movieDescription.textContent = desc;
      movieTime.textContent = time;
      movieImage.setAttribute('src', image);

      // Clear and repopulate genre tags
      movieGenre.innerHTML = '';
      genre.split(',').forEach(g => {
        const tag = document.createElement('span');
        tag.classList.add('genre-tag');
        tag.textContent = g.trim();
        movieGenre.appendChild(tag);
      });

      movieModal.classList.remove('hidden');
    }

    if (type === "series") {
      seriesTitle.textContent = title;
      seriesDescription.textContent = desc;
      seriesTime.textContent = time;
      seriesImage.setAttribute('src', image);

      // Clear and repopulate genre tags
      seriesGenre.innerHTML = '';
      genre.split(',').forEach(g => {
        const tag = document.createElement('span');
        tag.classList.add('genre-tag');
        tag.textContent = g.trim();
        seriesGenre.appendChild(tag);
      });

      const seasonCount = parseInt(card.getAttribute('data-seasons')) || 1;
      const seasonSelect = document.getElementById('season-select');
      seasonSelect.innerHTML = '';

      for (let i = 1; i <= seasonCount; i++) {
        const option = document.createElement('option');
        option.value = i;
        option.textContent = `Season ${i}`;
        seasonSelect.appendChild(option);
      }

      seriesModal.classList.remove('hidden');
    }
  });
});

// Close modals with close buttons
closeMovieModal.addEventListener('click', () => {
  movieModal.classList.add('hidden');
});

closeSeriesModal.addEventListener('click', () => {
  seriesModal.classList.add('hidden');
});