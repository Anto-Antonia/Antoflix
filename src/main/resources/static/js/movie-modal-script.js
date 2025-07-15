// Get modal elements
const movieModal = document.getElementById('movie-modal');
const closeMovieModal = document.getElementById('close-movie-modal');
const movieTitle = document.getElementById('movie-title');
const movieDescription = document.getElementById('movie-description');
const movieGenre = document.getElementById('movie-genre');
const movieTime = document.getElementById('movie-time');
const movieImage = document.getElementById('movie-image');
const movieModalContent = movieModal.querySelector('.modal-content');

// Add click event listener to each movie card
document.querySelectorAll('.movie-item').forEach(card => {
  card.addEventListener('click', () => {
    const details = card.querySelector('.movie-details');
    const image = card.querySelector('.movie-image')?.src || "images/default.png";

    const title = details?.dataset.title || "Unknown Title";
    const desc = details?.dataset.description || "No description.";
    const genre = details?.dataset.genre || "N/A";
    const time = details?.dataset.localdate || "No date.";

    // Fill modal content
    movieTitle.textContent = title;
    movieDescription.textContent = desc;
    movieTime.textContent = time;
    movieImage.setAttribute('src', image);

    // Populate genres as tags
    movieGenre.innerHTML = '';
    genre.split(',').forEach(g => {
      const tag = document.createElement('span');
      tag.classList.add('genre-tag');
      tag.textContent = g.trim();
      movieGenre.appendChild(tag);
    });

    // Show modal
    movieModal.classList.remove('hidden');
  });
});

// Close with X button
closeMovieModal.addEventListener('click', () => {
  movieModal.classList.add('hidden');
});

// Close by clicking outside modal content
movieModal.addEventListener('click', (e) => {
  if (!movieModalContent.contains(e.target)) {
    movieModal.classList.add('hidden');
  }
});