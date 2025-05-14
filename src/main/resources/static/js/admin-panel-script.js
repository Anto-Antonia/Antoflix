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
            <form id="addUserForm">
                <label for="username">Username:</label>
                <input type="text" id="username" placeholder="Enter username"><br>

                <label for="email">Email:</label>
                <input type="email" id="email" placeholder="Enter email"><br>

                <label for="password">Password:</label>
                <input type="password" id="password" placeholder="Enter password"><br>

                <button type="submit">Submit</button>
            </form>
            <div id="addUserMessage"></div>
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

    if(setting === "addUser"){
        setupAddUser();
    }

    if (setting === "getUserById") {
        setupGetUserById();
    }
}
    function setupAddUser(){
        const messageDiv = document.getElementById("addUserMessage");
        const form = document.getElementById("addUserForm")
        const userName = document.getElementById("username");
        const userEmail = document.getElementById("email");
        const userPassword = document.getElementById("password");

        form.addEventListener("submit", function (event){
            event.preventDefault(); // prevents the page to reload

            const username = userName.value.trim();
            const email = userEmail.value.trim();
            const password = userPassword.value.trim();

            if(!username || !email || !password){
                messageDiv.innerHTML = `<p style="color: red;"> All fields are required!</p>`;
                return;
            }

            const payload = {
                username: username,
                email: email,
                password: password
            };

            fetch("/api/v1/users/user", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(payload)
            })

            .then(response => {
                if(!response.ok){
                    return response.json().then(err => {
                        throw new Error(err.message || "Failed to add user.");
                    });
                }
                return response.json();
            })
            .then(data =>{
                messageDiv.innerHTML = `<p style="color: green;">User added successfully!</p>`;
                form.reset();  // clearing the inputs
            })
            .catch(error => {
                let errorText = error .message.includes("email") ?
                    "This email is already in use." : error.message;
                messageDiv.innerHTML = `<p style = "color: red;"> ${errorText}</p>`;
            });
        });

    }

    function setupGetUserById(){
        const userIdInput = document.getElementById("userId");
        const searchBtn = document.getElementById("searchUserBtn");
        const errorMessage = document.getElementById("error-message");
        const searchResult = document.getElementById("result-container");

        userIdInput.addEventListener("input", function() {
            if(!/^\d*$/.test(this.value)){
                errorMessage.textContent = "Only numbers are allowed!";
                this.style.borderColor = "red";
            } else{
                errorMessage.textContent = "";
                this.style.borderColor = "";
            }
        });

        searchBtn.addEventListener("click", function () {
            const userId = userIdInput.value.trim();
                if (userId === "" || isNaN(userId)) {
                    errorMessage.textContent = "Please enter a valid numeric ID!";
                    userIdInput.style.borderColor = "red";
                    return;
                }

                 errorMessage.textContent = ""; // Clear any error messages
                 fetch(`/api/v1/users/${userId}`)
                    .then(response=> {
                        if(!response.ok) throw new Error("User not found");
                        return response.json();
                    })
                    .then(data => {
                        searchResult.innerHTML = `
                            <div class="result-box">
                                <p><strong>User ID:</strong> ${userId}</p>              // added just the userId so I can return the ID I entered
                                <p><strong>Username:</strong> ${data.username}</p>      //in the search tab because the user ID does NOT exist in the UserResponse
                                <p><strong>Email:</strong> ${data.email}</p>
                                <p><strong>Role:</strong> ${data.roleName}</p>
                            </div>
                            `;
                    })
                    .catch(error => {
                        searchResult.innerHTML = `<p style="color: red;">${error.message}</p>`;
                    })
        });

    }






