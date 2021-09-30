const loginPageUrl = "/EvolutionTimeTableWeb/pages/LoginPage/login.html";
const viewTimeProblemPageUrl = "/EvolutionTimeTableWeb/pages/ViewTimeProblem/viewProblem.html";
const algorithmOperationPageUrl = "/EvolutionTimeTableWeb/pages/AlgorithmOperation/operation.html";
function goToViewProblemPage(problemId) {
    window.location.replace(viewTimeProblemPageUrl + "?id=" + problemId);
}

function goToAlgorithmOperationPage(problemId) {
    window.location.replace(algorithmOperationPageUrl + "?id=" + problemId);
}

function goToLoginPage()
{
  window.location.replace(loginPageUrl);
}

/*Cool Inputs */

class CoolInputs {
    static createCheckBox(checked) {
        const checkBox = document.createElement("input");
        checkBox.type = "checkbox";
        checkBox.className = "form-check-label";
        checkBox.checked = checked;

        return checkBox;
    }

    static createTextBox(text) {

        const textBox = document.createElement("input");
        textBox.className = "form-control";
        textBox.value = text;

        return textBox;

    }
}

/*Coool Table */
class CoolTable {

    constructor(headers) {

        this.tableElement = document.createElement("table");
        const thead = document.createElement("thead");
        const threadTr = document.createElement("tr");

        headers.forEach(header => {
            threadTr.innerHTML += `<th> ${header} </th>`;
        });

        thead.append(threadTr);
        this.tableElement.append(thead);
        this.tbody = document.createElement("tbody");
        this.tableElement.append(this.tbody);
        this.tableElement.className = "rtable";
        // this.tableElement.classList.add("rtable");
        this.headers = headers;
    }

    addCssClass(cssClass) {
        this.tableElement.classList.add(cssClass);
    }

    setTableClass(cssClass) {
        this.tableElement.className = cssClass;
    }

    addRow(rowData) {

        const tableRow = document.createElement("tr");
        rowData.forEach(data => {
            const tableData = document.createElement("td");
            tableData.append(data);
            tableRow.append(tableData);
        });

        this.tbody.append(tableRow);

    }

    addRowText(rowData) {

        const tableRow = document.createElement("tr");
        rowData.forEach(data => {
            tableRow.innerHTML += `<td>${data} </td> `;
        });

        this.tbody.append(tableRow);

    }

    getTableElement() {
        return this.tableElement;
    }
};


/*TabPane */
function openCity(evt, cityName) {
    var i, tabcontent, tablinks;
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }
    document.getElementById(cityName).style.display = "block";
    evt.currentTarget.className += " active";
}


/* bestSolutionClass    */

class BestSolutionGuiAdapter {
    constructor(bestSolutionDTO) {
        this.bestSolution = bestSolutionDTO;
    }

    populateTeachersComboBox(teachersComboBox) {
        teachersComboBox.innerHTML = "";
        this.bestSolution.teachers.forEach(teacher => {

            teachersComboBox.innerHTML += `<option value="${teacher.id}">teacher ${teacher.id}</option>"`;

        });

    }

    populateClassesComboBox(classesComboBox) {
        classesComboBox.innerHTML = "";
        this.bestSolution.classes.forEach(sclass => {

            classesComboBox.innerHTML += `<option value="${sclass.id}">class ${sclass.id}</option>"`;

        });
    }

    createClassesComboBox() {
        const classesComboBox = document.createElement("select");
        populateClassesComboBox(classesComboBox);
        return classesComboBox;
    }

    createTeachersComboBox() {
        const teachersComboBox = document.createElement("select");
        populateClassesComboBox(teachersComboBox);
        return teachersComboBox;

    }
    createDaysHeaders(days) {
        const daysHeaders = ["Hour"];

        for (let day = 1; day <= days; day++) {
            daysHeaders.push("Day " + day);
        }

        return daysHeaders;
    }

    createRawTable() {
        const rawTableHeaders = ["Teacher", "Class", "Subject", "Day", "Hour"];
        const rawTable = new CoolTable(rawTableHeaders);

        this.bestSolution.rawLeactuers.forEach(rawLeacture => {
            const rawLeactureData = [];
            rawLeactureData.push(rawLeacture.teacherId);
            rawLeactureData.push(rawLeacture.classId);
            rawLeactureData.push(rawLeacture.subjectId);
            rawLeactureData.push(rawLeacture.day);
            rawLeactureData.push(rawLeacture.hour);
            rawTable.addRow(rawLeactureData);
        });

        return rawTable.getTableElement();
    }

    createRuleResultsTable() {
        const ruleTableHeaders = ["Name", "Type", "Score", "Effect on fitness", "Max effect on fitness"];
        const rulesTable = new CoolTable(ruleTableHeaders);
        this.bestSolution.ruleResults.forEach(rule => {
            const ruleData = [];
            ruleData.push(rule.name);
            ruleData.push(rule.type);
            ruleData.push(rule.score);
            ruleData.push(rule.effectOnFitness);
            ruleData.push(rule.maxEffectOnFitness);
            rulesTable.addRow(ruleData);
        });

        return rulesTable.getTableElement();
    }

    createTeacherShecduleTable(teacherId) {
        const teacherShecdule = this.bestSolution.teachersShecdule.filter(shecdule => shecdule.id == teacherId)[0];
        const teachersTableCreator = new CoolTable(this.createDaysHeaders(teacherShecdule.days));
        const allLeactuers = teacherShecdule.leactures;
        const days = teacherShecdule.days;
        const hours = teacherShecdule.hours;
        //
        for (let hour = 0; hour < hours; hour++) {

            const hourRow = ["Hour " + (hour + 1)];

            for (let day = 0; day < days; day++) {
                const leactuersAtHourAndDay = allLeactuers[day][hour];

                let dayAndHourData = "";
                if (leactuersAtHourAndDay.length != 0) {
                    const firstLeacture = leactuersAtHourAndDay[0];
                    const sclass = firstLeacture.sclass;
                    const subject = firstLeacture.subject;

                    dayAndHourData = `class id : ${sclass.id} <br> class name : ${sclass.name} <br> <br> subject id : ${subject.id} <br> subject name : ${subject.name}`;
                    if (leactuersAtHourAndDay.length >= 2) {
                        dayAndHourData += "<br> <br>" + `collitions : ${leactuersAtHourAndDay.length}`;
                    }
                }

                hourRow.push(dayAndHourData);

            }

            teachersTableCreator.addRowText(hourRow);

        }

        return teachersTableCreator.getTableElement();

    }


    createClassShecduleTable(classId) {

        const classShecdule = this.bestSolution.classesShecdule.filter(shecdule => shecdule.id == classId)[0];
        const classTableCreator = new CoolTable(this.createDaysHeaders(classShecdule.days));
        const allLeactuers = classShecdule.leactures;
        const days = classShecdule.days;
        const hours = classShecdule.hours;
        //
        for (let hour = 0; hour < hours; hour++) {

            const hourRow = ["Hour " + (hour + 1)];

            for (let day = 0; day < days; day++) {
                const leactuersAtHourAndDay = allLeactuers[day][hour];

                let dayAndHourData = "";
                if (leactuersAtHourAndDay.length != 0) {
                    const firstLeacture = leactuersAtHourAndDay[0];
                    const teacher = firstLeacture.teacher;
                    const subject = firstLeacture.subject;

                    dayAndHourData = `teacher id : ${teacher.id} <br> teacher name : ${teacher.name} <br> <br> subject id : ${subject.id} <br> subject name : ${subject.name}`;
                    if (leactuersAtHourAndDay.length >= 2) {
                        dayAndHourData += "<br> <br>" + `collitions : ${leactuersAtHourAndDay.length}`;
                    }
                }

                hourRow.push(dayAndHourData);

            }

            classTableCreator.addRowText(hourRow);

        }

        return classTableCreator.getTableElement();

    }

};

class StopConditionName {
    static Time = "Time";
    static Generations = "Generations";
    static Fitness = "Fitness";
}

class MutationName {
    static Flipping = "Flipping";
    static Sizer = "Sizer";

}

class CrossOverName {
    static DayTime = "DayTimeOriented";
    static AspectOriented = "AspectOriented";
}

class SelectionName {
    static Truncation = "Truncation";
    static RouletteWheel = "RouletteWheel";
    static Tournemant = "Tournemant";
}

class ConfigurationValidator {
    static Valid = "";

    static CheckProbality(probality) {

        if (!(probality >= 0 && probality <= 1 && ConfigurationValidator.isFloat(probality))) {
            return "probality must be between [0,1]";
        }

        return Valid;
    }

    static checkTotalTupples(totalTupples) {
        if (!(totalTupples > 0 && ConfigurationValidator.isNumeric(totalTupples))) {
            return "Total Tupples must be atlist 1";
        }

        return ConfigurationValidator.Valid;
    }

    static checkCuttingPoints(cuttingPoints) {
        if (!(cuttingPoints > 0 && ConfigurationValidator.isNumeric(cuttingPoints))) {
            return "cutting points must be atlist 1";
        }

        return ConfigurationValidator.Valid;
    }

    static checkElitisem(elitisem) {
        if (!(elitisem >= 0 && this.isNumeric(elitisem))) {
            return "elitisem must be atlist 0";
        }

        return this.Valid;
    }

    static isFloat(str) {
        if (typeof str != "string") return false // we only process strings!  
        return !isNaN(str) && // use type coercion to parse the _entirety_ of the string (`parseFloat` alone does not do this)...
            !isNaN(parseInt(str)) // ...and ensure strings of whitespace fail
    }

    static isNumeric(str) {
        if (typeof str != "string" && typeof str != "number") return false // we only process strings!  
        return !isNaN(str) && // use type coercion to parse the _entirety_ of the string (`parseFloat` alone does not do this)...
            !isNaN(parseInt(str)) // ...and ensure strings of whitespace fail
    }
}



class AlgorithmState {
    static Running = "Running";
    static Paused = "Paused";
    static Stopped = "Stopped";
    static Finished = "Finished";
}

