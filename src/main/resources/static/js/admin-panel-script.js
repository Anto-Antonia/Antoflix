document.querySelectorAll(".control-pannel li").forEach(item => {
    item.addEventListener("click", function () {
        document.querySelectorAll(".control-pannel li").forEach(li => li.classList.remove("active"));
        this.classList.add("active");
    });
});

function showContent(setting) {
    const settingsContent = document.querySelector(".settings-content");

    // Define content for each setting
    const content = {
        addUser: `
            <h2>Add User</h2>
            <form>
                <label for="username">Username:</label>
                <input type="text" id="username" placeholder="Enter username"><br>

                <label for="email">Email:</label>
                <input type="email" id="email" placeholder="Enter email"><br>

                <label for="password">Password:</label>
                <input type="password" id="password" placeholder="Enter password"><br>

                <button type="submit">Submit</button>
            </form>
        `,
        getUserById: `
            <h2>Get User By ID</h2>
            <div class = "search-container">

                <label for="userId">User ID:</label>
                <input type="text" id="userId" placeholder="Enter user ID">

                <button type="button" id="searchUserBtn">Search</button>
                <div id="error-message" style="color: red;"></div>
            </div>
            <div id="result-container"></div>
        `,
        getAllUsers: `
            <h2>All Users</h2>
            <p>Display list of users here.</p>

            <div id="result-container"></div>
            `,

        updateUser: `
             <h2>Update User</h2>
             <p> Enter below the ID of the user you wish to make changes </p>
                   <br>
                           <form>
                               <label for="id">User ID:</label>
                               <input type="number" id="id" placeholder = "Enter user's ID"><br>

                               <label for="username">Username:</label>
                               <input type="text" id="username" placeholder="Enter username"><br>

                               <button type="submit">Submit</button>
                           </form>
                           <div id="result-container"></div>
                           `,

        deleteUser: `<h2>Delete User</h2>
                       <p>Enter user ID to delete.</p>
                       <br>
                           <form>
                               <label for="id"> User ID:</label>
                               <input type="number" id="deleteUser" placeholder="Enter the user's id"><br>

                               <button type="submit">Submit</button>
                           </form>
                           <div id="result-container"></div>
                           `,

        addRole: `
            <h2>Add Role</h2>
            <p>Form for adding a role if needed.</p>
                   <br>

                   <form>
                      <label for="id">Add Role: </label>
                      <input type="text" id="name" placeholder="Enter the name of the role"><br>

                         <button type="submit">Submit</button>
                   </form>
                   <div id="result-container"></div>
               `,
        addRoleToUser: `<h2>Assign Role</h2><p>Assign role form here.</p>`,
        getRoleById: `<h2>Get Role</h2><p>Search for role by ID.</p>`,
        deleteRole: `<h2>Delete Role</h2><p>Enter role ID to delete.</p>`,
    };

    // Update the right-side content
    settingsContent.innerHTML = content[setting] || "<h2>Option not found</h2>";

    if(setting === "getUserById"){
        const userIdInput = document.getElementById("userId");
        const errorMessage = document.getElementById("error-message");
        const searchButton = document.getElementById("searchUserBtn");
        const searchResult = document.getElementById("result-container");

        userIdInput.addEventListener("input", function () {
            if (!/^\d*$/.test(this.value)) { // Allow only numbers
                errorMessage.textContent = "Only numbers are allowed!";
                this.style.borderColor = "red";
            } else {
                errorMessage.textContent = "";
                this.style.borderColor = "";
            }
    });

    searchButton.addEventListener("click", function () {
        const userId = userIdInput.value.trim();
        if (userId === "" || isNaN(userId)) {
            errorMessage.textContent = "Please enter a valid numeric ID!";
            userIdInput.style.borderColor = "red";
            return;
        }

        errorMessage.textContent = ""; // Clear any error messages

        // Simulate fetching data (replace with actual API call)
        setTimeout(() => {
            searchResult.innerHTML = `
                <div class="result-box">
                    <p><strong>User ID:</strong> ${userId}</p>
                    <p><strong>Name:</strong> John Doe</p>
                    <p><strong>Email:</strong> johndoe@example.com</p>
                </div>
            `;
        }, 500);
    });
    }
}