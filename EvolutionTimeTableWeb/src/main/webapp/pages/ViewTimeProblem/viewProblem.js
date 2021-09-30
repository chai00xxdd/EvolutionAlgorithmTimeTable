//TimeProblem View
const attributesContent = document.querySelector("#attributesContent");
const teachersContent = document.querySelector("#teachersContent");
const subjectsContent = document.querySelector("#subjectsContent");
const classesContent = document.querySelector("#classesContent");
const pageTitle = document.querySelector("#pageTitle");
//users C Table
const usersRunningProblemDiv = document.querySelector("#usersRunningProblemDiv");
const usersProblemsTitle = document.querySelector("#usersProblemsTitle");
const usersProblemsTableHTML = document.querySelector("#usersProblemsTable");
const usersProblemsTable = new UsersProblemsProgressTable(usersProblemsTableHTML);
const RefreshRate = 300;


const rulesDiv = document.querySelector("#rulesDiv");

//NavBar
const operationLink = document.querySelector("#operationLink");
const confuigrationLink = document.querySelector("#confuigrationLink");



//Teachers
const teachersComboBox = document.querySelector("#teachersComoBox");
const teacherTableDiv = document.querySelector("#TeacherTableDiv");

//Classes
const classesComboBox = document.querySelector("#classesComoBox");
const classTableDiv = document.querySelector("#ClassTableDiv");

//BestSolution
var currentBestSolution = null;
const bestSolutionGeneralDetails = document.querySelector("#bestSolutionGeneralDetails");
const bestSolutionTabsPanel = document.querySelector("#BestSolutionTabsPanel");
const RulesTab = document.querySelector("#Rules");
const RawTab = document.querySelector("#RawTab");
const bestSolutionDiv = document.querySelector("#bestSolutionDiv");

//Config
const configDiv = document.querySelector("#configDiv");
const configTitle = document.querySelector("#configTitle");
const configContainer = document.querySelector("#configContainer");

$(function () {


    //clearInterval(chatInterval);
    //console.log("here dude");
    usersProblemsTableHTML.style.display = "none";
    configContainer.style.display = "none";
    bestSolutionDiv.style.display = "none";
    operationLink.href = `../AlgorithmOperation/operation.html?id=${getParemterFromUrl("id")}`;
    confuigrationLink.href = `../ConfuigrationPage/confuigration.html?id=${getParemterFromUrl("id")}`;
    getAndPopulateTimeProblem();
    getAndPopulateUsersAlgorithmProgress();
    setInterval(getAndPopulateUsersAlgorithmProgress, RefreshRate);


    teachersComboBox.addEventListener("change", function () {
        // console.log(teachersComboBox.value);
        populateTeacherTableById(teachersComboBox.value);
    })

    classesComboBox.addEventListener("change", function () {
        // console.log(classesComboBox.value);
        populateClassTableById(classesComboBox.value);
    })

});



function getAndPopulateTimeProblem() {
    var problemId = ServerUtils.getProblemIdFromUrl();

    $.ajax({
        method: 'GET',
        data: createParemterData(Attributes.Id, problemId),
        url: ServerUrls.TimeProblemServletUrl,
        timeout: 2000,
        success: function (response) {
            // console.log(response);
            //  console.log("got response");
            populateTeachers(response.timeProblem.teachers);
            populateSubjects(response.timeProblem.subjects);
            populateClasses(response.timeProblem.classes);
            populateRules(response.timeProblem.rules);
            populateAttributes(response.timeProblem.attributes);
            pageTitle.innerHTML = `<h1> Time Problem #${response.timeProblem.id} : Owner is ${response.owner} </h1>`;

        }
    });

}

function getAndPopulateUsersAlgorithmProgress() {

    var problemId = ServerUtils.getProblemIdFromUrl();

    $.ajax({
        method: 'GET',
        data: createParemterData(Attributes.Id, problemId),
        url: ServerUrls.UsersAlgorithmProgressServletUrl,
        timeout: 2000,
        success: function (usersAlgorithmProgressArray) {


            //populateUsersProgressOfProblem(usersAlgorithmProgressArray);
            /*
 if (usersAlgorithmProgressArray.length == 0) {
        usersRunningProblemDiv.innerHTML = " <h1> No Users Solving The Problem!!! </h1>";
    }
    else {
        usersRunningProblemDiv.innerHTML = "<h1>Users Solving The Problem </h1>";
        userProgressTable.addCssClass("cancelScrollPane");
        usersRunningProblemDiv.append(userProgressTable.getTableElement());
    }
            */

            if (usersAlgorithmProgressArray.length == 0) {
                usersProblemsTableHTML.style.display = "none";
                usersProblemsTitle.innerHTML = "No Users Solving The Problem!!!";
            }
            else {
                usersProblemsTitle.innerHTML = "Users Solving The Problem";
                usersProblemsTableHTML.style.display = "block";
                usersProblemsTable.populateOrUpdateUsersProgress(usersAlgorithmProgressArray);
            }
        }

    });

}

function getAndPopulateAlgorithmConfuigration(userName, problemId) {

    const algorithmConfigRequestData = new URLSearchParams();
    algorithmConfigRequestData.append(Attributes.Id, problemId);
    algorithmConfigRequestData.append(Attributes.Username, userName);

    $.ajax({
        method: "GET",
        url: ServerUrls.AlgorithmConfigServletUrl,
        data: algorithmConfigRequestData.toString(),
        timeout: 2000,
        error: function (errorObject) {
            bestSolutionErrorLabel.textContent = errorObject.responseText;

        },
        success: function (algorithmConig) {

            const algorithmConfigGuiAdatper = new AlgorithmConfigGuiAdapter(algorithmConig);
            configContainer.style.display = "block";
            bestSolutionDiv.style.display = "none";
            configTitle.innerHTML = "Config Of User " + userName;
            configDiv.innerHTML = "<h2>Config Table </h2>";
            configDiv.append(algorithmConfigGuiAdatper.createConfigTable());
            configDiv.innerHTML += "<h2>Mutations Table </h2>";
            configDiv.append(algorithmConfigGuiAdatper.createMutationsTable());

        }
    });

}

function populateUsersProgressOfProblem(usersAlgorithmProgressArray) {
    const usersProgressTableHeaders = ["User", "Generation", "Best Fitness", "Best Solution", "Algorithm Config"];
    const userProgressTable = new CoolTable(usersProgressTableHeaders);

    usersAlgorithmProgressArray.forEach(userProgress => {
        const userProgressRow = [];
        const progress = userProgress.algorithmProgress;

        userProgressRow.push(userProgress.userName);
        userProgressRow.push(progress.generation);
        userProgressRow.push(progress.fitness);

        const showConfigButton = document.createElement("button");
        showConfigButton.innerHTML = "Show Config";
        showConfigButton.userName = userProgress.userName;
        showConfigButton.className = "btn btn-info";
        const problemId = getParemterFromUrl("id");

        showConfigButton.addEventListener("click", function () {

            const userName = this.userName;
            getAndPopulateAlgorithmConfuigration(userName, problemId);

        });



        const showBestSolutionButton = document.createElement("button");
        showBestSolutionButton.innerHTML = "Show Solution";
        showBestSolutionButton.userName = userProgress.userName;
        showBestSolutionButton.className = "btn btn-info";

        showBestSolutionButton.addEventListener("click", function () {

            getAndPopulateBestSolution(this.userName, problemId);

        }); //button eventListener Ends

        if (progress.fitness == 0) {
            showBestSolutionButton.disabled = true;
            //showConfigButton.disabled = true;
        }

        userProgressRow.push(showBestSolutionButton);
        userProgressRow.push(showConfigButton);

        userProgressTable.addRow(userProgressRow);
    });

    if (usersAlgorithmProgressArray.length == 0) {
        usersRunningProblemDiv.innerHTML = " <h1> No Users Solving The Problem!!! </h1>";
    }
    else {
        usersRunningProblemDiv.innerHTML = "<h1>Users Solving The Problem </h1>";
        userProgressTable.addCssClass("cancelScrollPane");
        usersRunningProblemDiv.append(userProgressTable.getTableElement());
    }



}

function getAndPopulateBestSolution(userName, problemId) {
    $.ajax({
        method: "GET",
        url: ServerUrls.BestSolutionServletUrl,
        data: createParemterData(Attributes.Id, problemId) + "&" + createParemterData(Attributes.Username, userName),
        timeout: 2000,
        error: function (errorObject) {
            bestSolutionErrorLabel.textContent = errorObject.responseText;

        },
        success: function (bestSolution) {

            currentBestSolution = bestSolution;
            //   console.log(bestSolution);
            populateRulesResult(bestSolution);
            populateRaw(bestSolution)
            populateAndInitComboBoxes(bestSolution);

            populateTeacherTableById(bestSolution.teachers[0].id);
            populateClassTableById(bestSolution.classes[0].id);

            bestSolutionGeneralDetails.innerHTML = `Solution Of User : <b>${bestSolution.userName}</b> <br> Generation : ${bestSolution.generation} <br> Fitness : ${bestSolution.fitness}`
                + ` <br> Number Of Leactures : ${bestSolution.numberOfLeactuers}`;
            document.querySelector("#RawTabButton").click();
            configContainer.style.display = "none";
            bestSolutionDiv.style.display = "block";

        }
    }); //Ajax Ends
}

function createAttributeRowData(attribute) {

    const attributeRowData = [];
    attributeRowData.push(attribute.attribute);
    attributeRowData.push(attribute.value);
    return attributeRowData;
}

function populateAttributes(attributes) {
    // console.log(attributes);
    const attributesHeaders = ["Attribute", "Value"];
    const attributesTable = new CoolTable(attributesHeaders);
    attributes.forEach(attribute => { attributesTable.addRow(createAttributeRowData(attribute)) });
    attributes.innerHTML = "";
    //attributesTable.setTableClass("table table-bordered table-dark");
    attributesContent.append(attributesTable.getTableElement());


}
function createRuleRowData(rule) {
    const ruleRowData = [];

    ruleRowData.push(rule.name);
    ruleRowData.push(rule.type);
    ruleRowData.push(rule.paremeters);

    return ruleRowData;
}

function populateRules(rules) {

    rulesDiv.innerHTML = "";
    const rulesHeaders = ["Name", "Type", "Paremters"];
    const rulesTable = new CoolTable(rulesHeaders);

    rules.forEach(rule => { rulesTable.addRow(createRuleRowData(rule)) });
    //   rulesTable.setTableClass("table table-bordered ");
    rulesDiv.append(rulesTable.getTableElement());
}//



function createSubjectRowData(subject) {

    const subjectRowData = [];
    subjectRowData.push(subject.id);
    subjectRowData.push(subject.name)

    return subjectRowData;
}

function populateSubjects(subjects) {
    const subjectsHeaders = ["id", "name"];
    const subjectsTable = new CoolTable(subjectsHeaders);
    subjects.forEach(subject => { subjectsTable.addRow(createSubjectRowData(subject)) });

    subjectsContent.innerHTML = "";
    //subjectsTable.setTableClass("table table-bordered table-dark");
    subjectsContent.append(subjectsTable.getTableElement());

}


function createTeacherRowData(teacher) {
    const teacherTableRowData = [];
    teacherTableRowData.push(teacher.id);
    teacherTableRowData.push(teacher.name);
    teacherTableRowData.push(teacher.coursesString);
    teacherTableRowData.push(teacher.workingHoursPrefernce);

    return teacherTableRowData;

}
function populateTeachers(teachers) {


    const teachersTableHeaders = ["id", "name", "courses", "Working Hours Prefernce"];
    const teachersTable = new CoolTable(teachersTableHeaders);
    teachers.forEach(teacher => {
        teachersTable.addRow(createTeacherRowData(teacher));
    });

    teachersContent.innerHTML = "";
    // teachersTable.setTableClass("table table-bordered table-dark");
    teachersContent.append(teachersTable.getTableElement());

}

function createClassRowData(sclass) {
    const classRowData = [];
    classRowData.push(sclass.id);
    classRowData.push(sclass.name);
    classRowData.push(sclass.requirmentsString);

    return classRowData;

}
function populateClasses(classes) {

    const classesHeaders = ["id", "name", "requirments"];
    const classesTable = new CoolTable(classesHeaders);
    classes.forEach(sclass => { classesTable.addRow(createClassRowData(sclass)) });

    classesContent.innerHTML = "";
    // classesTable.setTableClass("table table-bordered table-dark");
    classesContent.append(classesTable.getTableElement());
}


//User BestSolution

function populateAndInitComboBoxes(bestSolution) {
    teachersComboBox.innerHTML = "";
    classesComboBox.innerHTML = "";
    const guiAdapter = new BestSolutionGuiAdapter(bestSolution);
    guiAdapter.populateClassesComboBox(classesComboBox);
    guiAdapter.populateTeachersComboBox(teachersComboBox);
}
function populateTeacherTableById(teacherId) {

    teacherTableDiv.innerHTML = "";
    teacherTableDiv.append(new BestSolutionGuiAdapter(currentBestSolution).createTeacherShecduleTable(teacherId));

}

function populateClassTableById(classId) {

    classTableDiv.innerHTML = "";
    classTableDiv.append(new BestSolutionGuiAdapter(currentBestSolution).createClassShecduleTable(classId));

}

function populateRaw(bestSolution) {

    RawTab.innerHTML = "";
    RawTab.append(new BestSolutionGuiAdapter(bestSolution).createRawTable());
}
function populateRulesResult(bestSolution) {

    RulesTab.innerHTML = "";
    RulesTab.append(new BestSolutionGuiAdapter(bestSolution).createRuleResultsTable());

}
