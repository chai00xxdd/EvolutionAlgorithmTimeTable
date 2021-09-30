

const welcomeTitle = document.querySelector("#WelcomeTitle");
const usersList = document.querySelector("#UsersList");
const timeProblemsDiv = document.querySelector("#timeProblemsDiv");
const timeProblemsError = document.querySelector("#timeProblemsUploadError");
const fileInput = document.querySelector("#fileInput");
const uploadForm = document.querySelector("#uploadForm");
const RefreshDelayInMs = 500;
const TimeTableRefreshInMs = 300;
var currentTimeProblemsInTable = -1;

//timeProblemsTable
const userTimeProblemsTable = new UsersTimeProblemsTable(timeProblemsDiv);

$(function () {
    //  console.log("loading userPage");
    populateMyUserName();
    getAndPouplateUsers();
    getAndpopulateTimeProblems();
    setInterval(getAndPouplateUsers, RefreshDelayInMs);
    setInterval(getAndpopulateTimeProblems, TimeTableRefreshInMs);

    //clearInterval(chatInterval);
});

fileInput.addEventListener("change", function () {
    timeProblemsError.innerHTML = "";
});

$("#uploadForm").submit(function () {

    timeProblemsError.innerHTML = "";
    if (document.querySelector("#fileInput").value == "") {
        timeProblemsError.innerHTML = "please choose file first";
        return false;
    }
    var file1 = fileInput.files[0];
    var formData = new FormData();
    formData.append("fake-key-1", file1);
    $.ajax({
        method: 'POST',
        data: formData,
        url: this.action,
        processData: false, // Don't process the files
        contentType: false, // Set content type to false as jQuery will tell the server its a query string request
        timeout: 4000,
        success: function (response) {
            // console.log(response);
            if (response.error == "") {
                timeProblemsError.innerHTML = `<h2> ${response.data} </h2>`;
                getAndpopulateTimeProblems();
            }
            else {
                const errorTitle = response.error;
                const errors = response.data;
                var first = true;
                var ErrosUl = document.createElement("ul");
                timeProblemsError.innerHTML = `<h2>${errorTitle} </h2>`

                errors.forEach(error => {
                    // console.log(error);
                    ErrosUl.innerHTML += (`<li> ${error} </li>`);
                });
                if (errors.length != 0) {
                    timeProblemsError.innerHTML += "Errors List:";
                    timeProblemsError.append(ErrosUl);
                }
            }

        }
    });


    document.querySelector("#fileInput").value = "";

    // return value of the submit operation
    // by default - we'll always return false so it doesn't redirect the user.
    return false;
});



function getAndPouplateUsers() {

    $.ajax({
        url: ServerUrls.UsersServletUrl,
        timeout: 2000,
        error: function (errorObject) {
            //window.location.replace(loginPageUrl);
        },
        success: function (usersData) {
            populateUsers(usersData);
        }
    });

    // by default - we'll always return false so it doesn't redirect the user.
    return false;

}

function populateUsers(usersData) {

    usersList.innerHTML = "";
    usersData.forEach(user => {
        usersList.innerHTML += `<li> ${user.userName} </li>`;
    });

}

function populateMyUserName() {
    var userName = getCookie("username");
    if (userName != "") {
        welcomeTitle.innerHTML = "Welcome " + userName;
    }
    else {
        console.log("no name cookie");
    }
}

function createTimeProblemTable(problemShortDto) {


    const timeProblemsTableHeaders = ["Threads Alive Solving", "D", "H", "T", "C", "S", "hard rules", "soft rules", "owner", "best fitness", "View", "Solve", "Status"];
    const timeProblemsTable = new CoolTable(timeProblemsTableHeaders);


    problemShortDto.forEach(problemDTO => {

        const newRow = [];

        newRow.push(problemDTO.howManySolving);
        newRow.push(problemDTO.days);
        newRow.push(problemDTO.hours);
        newRow.push(problemDTO.teachers);
        newRow.push(problemDTO.classes);
        newRow.push(problemDTO.subjects);
        newRow.push(problemDTO.hardRules);
        newRow.push(problemDTO.softRules);
        newRow.push(problemDTO.owner);
        newRow.push(problemDTO.bestFitness);

        const viewButton = document.createElement("button");
        const solveButton = document.createElement("button");

        solveButton.textContent = "Solve Me!!!";
        solveButton.addEventListener("click", function () {
            goToAlgorithmOperationPage(problemDTO.id);

        });

        viewButton.textContent = "View Me!!!";
        viewButton.addEventListener("click", function () {
            goToViewProblemPage(problemDTO.id);
        })

        viewButton.className = "btn btn-info";
        solveButton.className = "btn btn-primary";

        newRow.push(viewButton);
        newRow.push(solveButton);
        newRow.push(problemDTO.algorithmState);
        timeProblemsTable.addRow(newRow);

    });
    timeProblemsTable.getTableElement().id = "timeProblemsTable";
    return timeProblemsTable.getTableElement();

}

function getAndpopulateTimeProblems() {

    $.ajax({

        url: ServerUrls.TimeProblemsShortDTOServletUrl,
        timeout: 2000,
        success: function (timeProblemsShortDTO) {

            userTimeProblemsTable.populateOrUpdateRows(timeProblemsShortDTO);

            /*
                
                if (currentTimeProblemsInTable == timeProblemsShortDTO.length) {
                
                   // console.log("returning");
                    return;
                }
                
                currentTimeProblemsInTable = timeProblemsShortDTO.length;
                */

            //const timeTableDTOS = createTimeProblemTable(timeProblemsShortDTO);
            //timeProblemsDiv.innerHTML = "";

            //timeProblemsDiv.append(timeTableDTOS);
        }
    });

}




