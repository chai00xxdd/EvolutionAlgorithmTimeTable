
const runButton = document.querySelector("#RunButton");
const resumeButton = document.querySelector("#ResumeButton");
const stopButton = document.querySelector("#StopButton");
const pauseButton = document.querySelector("#PauseButton");
const fitnessLabel = document.querySelector("#fitness");
const generationLabel = document.querySelector("#generations");
const timeLabel = document.querySelector("#time");
const AlgorithmProgressRefreshRate = 100;



const bestSolutionErrorLabel = document.querySelector("#bestSolutionErrorLabel");
const bestSolutionButton = document.querySelector("#bestSolutionButton");



runButton.addEventListener("click", runAlgorithm);
resumeButton.addEventListener("click", resumeAlgorithm);
stopButton.addEventListener("click", stopAlgorithm);
pauseButton.addEventListener("click", pauseAlgorithm);
bestSolutionButton.addEventListener("click", getAndPopulateBestSolution);

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

//NavBar
const viewProblemLink = document.querySelector("#viewProblemLink");
const confuigrationLink = document.querySelector("#confuigrationLink");

//stopConditions Progress;
const stopConitionsProgressDiv = document.querySelector("#stopConitionsProgressDiv");

//Operation
const operationMessage = document.querySelector("#operationMessage");


var bestSolutionsChart;
var lastAlgorithmProgress = null;



function getAndPopulateGraph() {

const graphRequestParameters = new URLSearchParams();
graphRequestParameters.append(Attributes.Id,ServerUtils.getProblemIdFromUrl());
graphRequestParameters.append(Attributes.Graph,true);

    $.ajax({
        url: ServerUrls.AlgorithmOperationServletUrl,
        data: graphRequestParameters.toString(),
        timeout: 2000,
        error: function (errorObject) {
            //window.location.replace(loginPageUrl);
        },
        success: function (progressGraph) {

            const fitnessAxis = [];
            const generationAxis = [];
            progressGraph.forEach(progress => {
                generationAxis.push(progress.generation);
                fitnessAxis.push(progress.fitness);
            });

            populateBestSolutionChart(generationAxis, fitnessAxis);
            /*
      algorithmProgress
    private double fitness;
    private int generation;
    private int timePassed;
    private AlgorithmState algorithmState; */



            //yaxis array bestSolutionsChart.data.datasets[0].data
            //x axix array estSolutionsChart.data.labels

        }
    });

}



$(function () {//

    operationMessage.innerHTML = "";
    // clearInterval(chatInterval);
    // clearBestSolutionChart();
    initBestSolutionChart();
    getAndPopulateGraph();
    viewProblemLink.href = `../ViewTimeProblem/viewProblem.html?id=${getParemterFromUrl("id")}`;
    confuigrationLink.href = `../ConfuigrationPage/confuigration.html?id=${getParemterFromUrl("id")}`;
    setInterval(getAndPopulateAlgorithmProgress, AlgorithmProgressRefreshRate);



    teachersComboBox.addEventListener("change", function () {
        // console.log(teachersComboBox.value);
        populateTeacherTableById(teachersComboBox.value);
    })

    classesComboBox.addEventListener("change", function () {
        // console.log(classesComboBox.value);
        populateClassTableById(classesComboBox.value);
    })
    document.querySelector("#RawTabButton").click();
});

function runAlgorithm() {
    const problemId = ServerUtils.getProblemIdFromUrl();
    const toClearTableChart = lastAlgorithmProgress && lastAlgorithmProgress.algorithmState != "Running";
    const runAlgorithmRequestData = new URLSearchParams();

    runAlgorithmRequestData.append(Attributes.Id, problemId);
    runAlgorithmRequestData.append(Attributes.Operation, Operations.Run);

    $.ajax({
        method: "POST",
        url: ServerUrls.AlgorithmOperationServletUrl,
        data: runAlgorithmRequestData.toString(),
        timeout: 2000,
        error: function (errorObject) {

        },
        success: function (algorithmProgress) {

            if (toClearTableChart) {
                clearBestSolutionChartData();
            }
        }
    });

}

function stopAlgorithm() {
    const problemId = ServerUtils.getProblemIdFromUrl();
    const stopAlgorithmRequestData = new URLSearchParams();

    stopAlgorithmRequestData.append(Attributes.Id, problemId);
    stopAlgorithmRequestData.append(Attributes.Operation, Operations.Stop);

    $.ajax({
        method: "POST",
        url: ServerUrls.AlgorithmOperationServletUrl,
        data: stopAlgorithmRequestData.toString(),
        timeout: 2000,
        error: function (errorObject) {

        },
        success: function (algorithmProgress) {

        }
    });
}

function getAndPopulateBestSolution() {
    const problemId = ServerUtils.getProblemIdFromUrl();
    const userName = getMyUserName();

    const getBestSolutionRequestData = new URLSearchParams();

    getBestSolutionRequestData.append(Attributes.Id, problemId);
    getBestSolutionRequestData.append(Attributes.Username, userName);

    $.ajax({
        method: "GET",
        url: ServerUrls.BestSolutionServletUrl,
        data: getBestSolutionRequestData.toString(),
        timeout: 2000,
        error: function (errorObject) {
            bestSolutionErrorLabel.textContent = errorObject.responseText;

        },
        success: function (bestSolution) {
            currentBestSolution = bestSolution;
            bestSolutionErrorLabel.textContent = "";
            // console.log(bestSolution);
            populateRulesResult(bestSolution.ruleResults);
            populateRaw(bestSolution.rawLeactuers)
            populateAndInitComboBoxes(bestSolution);

            populateTeacherTableById(bestSolution.teachers[0].id);
            populateClassTableById(bestSolution.classes[0].id);

            bestSolutionGeneralDetails.innerHTML = `Generation : ${bestSolution.generation} <br> Fitness : ${bestSolution.fitness}`
                + ` <br> Number Of Leactures : ${bestSolution.numberOfLeactuers}`;

        }
    });

}




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

function populateRaw(rawLeactures) {

    RawTab.innerHTML = "";
    RawTab.append(new BestSolutionGuiAdapter(currentBestSolution).createRawTable());
}
function populateRulesResult(ruleResults) {

    RulesTab.innerHTML = "";
    RulesTab.append(new BestSolutionGuiAdapter(currentBestSolution).createRuleResultsTable());

}
function resumeAlgorithm() {
    const problemId = ServerUtils.getProblemIdFromUrl();
    const resumseAlgorithmRequestData = new URLSearchParams();

    resumseAlgorithmRequestData.append(Attributes.Id, problemId);
    resumseAlgorithmRequestData.append(Attributes.Operation, Operations.Resume);


    $.ajax({
        method: "POST",
        url: ServerUrls.AlgorithmOperationServletUrl,
        data: resumseAlgorithmRequestData.toString(),
        timeout: 2000,
        error: function (errorObject) {

        },
        success: function () {

        }
    });

}

function pauseAlgorithm() {

    const problemId = ServerUtils.getProblemIdFromUrl();
    const pauseAlgorithmRequestData = new URLSearchParams();

    pauseAlgorithmRequestData.append(Attributes.Id, problemId);
    pauseAlgorithmRequestData.append(Attributes.Operation, Operations.Pause);

    $.ajax({
        method: "POST",
        url: ServerUrls.AlgorithmOperationServletUrl,
        data: pauseAlgorithmRequestData.toString(),
        timeout: 2000,
        error: function (errorObject) {

        },
        success: function () {

        }
    });
}

function getAndPopulateAlgorithmProgress() {
    $.ajax({
        url: ServerUrls.AlgorithmOperationServletUrl,
        data: createParemterData(Attributes.Id, ServerUtils.getProblemIdFromUrl()),
        timeout: 2000,
        error: function (errorObject) {
            //window.location.replace(loginPageUrl);
        },
        success: function (algorithmProgress) {
              //console.log(algorithmProgress);

            lastAlgorithmProgress = algorithmProgress;
            enableOrDisableOperationsButtons(algorithmProgress);

            /*
      algorithmProgress
    private double fitness;
    private int generation;
    private int timePassed;
    private AlgorithmState algorithmState; */
            timeLabel.textContent = "time : " + ((Math.round((algorithmProgress.timePassed / 1000) * 100) / 100).toFixed(1) + "");
            generationLabel.innerHTML = "generations : <br>" + algorithmProgress.generation;
            const fixedFitness = (algorithmProgress.fitness + "").substr(0, 4);
            fitnessLabel.textContent = "fitness : " + fixedFitness;



            populateStopConditionsProgress(algorithmProgress.stopConditionsProgress);

            const lastGeneration = bestSolutionsChart.data.labels.slice(-1);
            if (lastGeneration != algorithmProgress.generation) {
                addDataToChart(bestSolutionsChart, algorithmProgress.generation, fixedFitness);
            }
            //yaxis array bestSolutionsChart.data.datasets[0].data
            //x axix array estSolutionsChart.data.labels

        }
    });
}



function enableOrDisableOperationsButtons(algorithmProgress) {
    const noStopConditions = algorithmProgress.noStopConditions;
    const algorithmState = algorithmProgress.algorithmState;
    const algorithmFirstGeneration = algorithmProgress.fitness == 0 && algorithmProgress.generation == 0;

    runButton.disabled = algorithmState == AlgorithmState.Running || noStopConditions;
    resumeButton.disabled = algorithmState == AlgorithmState.Running || algorithmProgress.stopConditionReached || noStopConditions || algorithmFirstGeneration;
    pauseButton.disabled = noStopConditions;



    if (noStopConditions && algorithmState == AlgorithmState.Running) {
        stopAlgorithm();

    }
    if (!noStopConditions) {
        operationMessage.innerHTML = "";
    }
    else if (noStopConditions && operationMessage.innerHTML == "") {

        const linkToStopConditions = document.createElement("a");
        linkToStopConditions.innerHTML = "please choose atlist one stop condition";
        linkToStopConditions.style.color = "#0000EE";
        linkToStopConditions.href = confuigrationLink.href;
        operationMessage.innerHTML = "";
        operationMessage.append(linkToStopConditions);

    }

}


function populateStopConditionsProgress(stopConditions) {
    //console.log(stopConditions);
    stopConitionsProgressDiv.innerHTML = "";
    stopConditions.forEach(stopCondition => {
        if (!stopCondition.enabled) {
            return;
        }
        const stopConditionDiv = document.createElement("div");
        const stopCondLabel = document.createElement("label");
        stopCondLabel.innerHTML = "Stop By " + stopCondition.name + " " + stopCondition.value;

        const stopConditionProgress = document.createElement("progress");
        stopConditionProgress.max = "1";
        stopConditionProgress.value = stopCondition.progress;

        stopConditionDiv.append(stopCondLabel);
        stopConditionDiv.append(stopConditionProgress);

        stopConitionsProgressDiv.append(stopConditionDiv);


        //hide or show operations buttons



    });
}

function addDataToChart(chart, x, y) {
    chart.data.labels.push(x);
    chart.data.datasets.forEach((dataset) => {
        dataset.data.push(y);
    });
    chart.update();
}

function clearBestSolutionChartData() {
    const generationAxis = [];
    const fitnessAxis = [];
    populateBestSolutionChart(generationAxis, fitnessAxis);
}

function populateBestSolutionChart(generationAxis = [], fitnessAxis = []) {

    bestSolutionsChart.data.labels = generationAxis;
    bestSolutionsChart.data.datasets[0].data = fitnessAxis;
    bestSolutionsChart.update();
}

function initBestSolutionChart(generationAxis = [], fitnessAxis = []) {
    // xValues = [1, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150, 200];
    //yValues = [0.1, 0.8, 0.8, 0.9, 0.9, 0.9, 0.10, 0.11, 0.14, 0.14, 0.15, 0.300];

    //console.log("creating bestSolutionChart");
    bestSolutionsChart = new Chart("myChart", {
        type: "line",
        data: {
            labels: generationAxis,
            datasets: [{
                fill: false,
                lineTension: 0,
                backgroundColor: "rgba(0,0,255,1.0)",
                borderColor: "rgba(0,0,255,1.0)",
                data: fitnessAxis
            }]
        },
        options: {
            elements: {
                point: {
                    radius: 0
                }

            },
            legend: { display: false },
            scales: {
                yAxes: [{
                    ticks: { min: 0, max: 1 }
                    , scaleLabel: {
                        display: true,
                        labelString: 'Fitness'
                    }
                }],
                xAxes: [{
                    scaleLabel: {
                        display: true,
                        labelString: 'Generations'
                    }
                }]
            }
        }
    });
}
