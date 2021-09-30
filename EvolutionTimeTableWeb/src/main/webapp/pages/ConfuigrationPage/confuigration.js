
//console.log("here")
//NavBar
const operationLink = document.querySelector("#operationLink");
const viewProblemLink = document.querySelector("#viewProblemLink");

//Selection
const selectionForm = document.querySelector("#selectionForm");
const selectionComboBox = document.querySelector("#selectionComboBox");
const elitismTextBox = document.querySelector("#ellitismTextBox");
const selectionParemterTextBox = document.querySelector("#selectionParemterTextBox");
const selectionErrorLabel = document.querySelector("#selectionLabel");
const selectionParameterNameLabel = document.querySelector("#selectionParameterNameLabel");

//CrossOver
const crossOverForm = document.querySelector("#crossOverForm");
const crossoverComboBox = document.querySelector("#crossoverComboBox");
const cuttingPointsTextBox = document.querySelector("#cuttingPointsTextBox");
const aspectComboBox = document.querySelector("#aspectComboBox");
const crossoverErrorLabel = document.querySelector("#crossoverLabel");
const aspectNameLabel = document.querySelector("#aspectNameLabel");



//Mutations
const mutationsForm = document.querySelector("#mutationsForm");
const mutationsTableDiv = document.querySelector("#mutationsTableDiv");
const mutationComboBox = document.querySelector("#mutationComboBox");
const mutationProbalityTextBox = document.querySelector("#mutationProbalityTextBox");
const mutationParameterTextBox = document.querySelector("#mutationParameterTextBox");
const mutationsErrorLabel = document.querySelector("#mutationsLabel");
const componentDiv = document.querySelector("#componentDiv");
const mutationParameterName = document.querySelector("#mutationParameterName");
const mutationFlippingComponentComboBox = document.querySelector("#mutationFlippingComponentComboBox");

//Population
const populationForm = document.querySelector("#populationForm");
const populationTextBox = document.querySelector("#populationTextBox");
const populationErrorLabel = document.querySelector("#populationLabel");

//StopConditoins
const stopConditionsTableDiv = document.querySelector("#stopConditionsTableDiv");
const stopConditionsErrorLabel = document.querySelector("#stopConditionsErrorLabel");

//Config

var currentConfig = null;

function clearErrorsLabel() {
    crossoverErrorLabel.innerHTML = "";
    selectionErrorLabel.innerHTML = "";
    populationErrorLabel.innerHTML = "";
    mutationsErrorLabel.innerHTML = "";
    stopConditionsErrorLabel.innerHTML = "";
}


$(function () {

    //clearInterval(chatInterval);
    operationLink.href = `../AlgorithmOperation/operation.html?id=${getParemterFromUrl("id")}`;
    viewProblemLink.href = `../ViewTimeProblem/viewProblem.html?id=${getParemterFromUrl("id")}`;

    crossoverErrorLabel.innerHTML = "";
    selectionErrorLabel.innerHTML = "";
    populationErrorLabel.innerHTML = "";


    getAndPopulateConfig();
});

selectionComboBox.addEventListener("change", function () {

    HideOrShowSelectionParameter(selectionComboBox.value);
    selectionErrorLabel.innerHTML = "";
});

mutationComboBox.addEventListener("change", function () {

    updateMutationGuiByComboBoxValue();
})

function updateMutationGuiByComboBoxValue() {
    const showComponentComboBox = mutationComboBox.value == MutationName.Flipping;
    const componentDisplay = showComponentComboBox ? "block" : "none";
    componentDiv.style.display = componentDisplay;

    if (mutationComboBox.value == MutationName.Flipping) {
        mutationParameterName.innerHTML = "MaxTupples";
    }
    else {
        mutationParameterName.innerHTML = "TotalTupples";
    }
}

function HideOrShowSelectionParameter(selectionName) {

    let toDisplayParamter = selectionName != SelectionName.RouletteWheel ? "block" : "none";

    selectionParameterNameLabel.style.display = toDisplayParamter;
    selectionParemterTextBox.style.display = toDisplayParamter;

    selectionParameterNameLabel.innerHTML = "TopPercent";
    if (selectionName == SelectionName.Tournemant) {
        selectionParameterNameLabel.innerHTML = "PTE";
    }


}





$(mutationsForm).submit(function () {


    const problemId = ServerUtils.getProblemIdFromUrl();

    const paramter = mutationParameterTextBox.value;
    const mutationName = mutationComboBox.value;
    const flippingComponent = mutationFlippingComponentComboBox.value;
    const probality = mutationProbalityTextBox.value;


    const mutationData = new URLSearchParams();

    mutationData.append(Attributes.Id, problemId);
    mutationData.append(Attributes.Parameter, paramter);
    mutationData.append(Attributes.Name, mutationName);
    mutationData.append(Attributes.Component, flippingComponent);
    mutationData.append(Attributes.Probality, probality);
    mutationData.append(Attributes.Operation, Operations.Add);




    $.ajax({
        method: "POST",
        url: ServerUrls.MutationsServletUrl,
        data: mutationData.toString(),
        timeout: 2000,
        error: function (errorObject) {
            // bestSolutionErrorLabel.textContent = errorObject.responseText;
            clearErrorsLabel();
            mutationsErrorLabel.innerHTML = errorObject.responseText;
            // getAndPopulateConfig();

        },
        success: function () {

            clearErrorsLabel();
            getAndPopulateConfig();//to update the poor table
        }
    });


    return false;
});

$(crossOverForm).submit(function () {

    const problemId = ServerUtils.getProblemIdFromUrl();
    const orientation = aspectComboBox.value;
    const cuttingPoints = cuttingPointsTextBox.value;
    const crossOverName = crossoverComboBox.value;

    const crossOverData = new URLSearchParams();
    crossOverData.append(Attributes.Id, problemId);
    crossOverData.append(Attributes.CuttingPoints, cuttingPoints);
    crossOverData.append(Attributes.Parameter, orientation);
    crossOverData.append(Attributes.Name, crossOverName);


    $.ajax({
        method: "POST",
        url: ServerUrls.CrossOverServletUrl,
        data: crossOverData.toString(),
        timeout: 2000,
        error: function (errorObject) {
            // bestSolutionErrorLabel.textContent = errorObject.responseText;
            clearErrorsLabel();
            crossoverErrorLabel.innerHTML = errorObject.responseText;
            // getAndPopulateConfig();

        },
        success: function () {

            clearErrorsLabel();
            crossoverErrorLabel.innerHTML = "Updated CrossOver Successfully";
        }
    });

    return false;


});

$(selectionForm).submit(function () {
    const problemId = ServerUtils.getProblemIdFromUrl();
    const ellitsim = elitismTextBox.value;
    const parameter = selectionParemterTextBox.value;

    const selectionData = new URLSearchParams();

    selectionData.append(Attributes.Name, selectionComboBox.value);
    selectionData.append(Attributes.Elittism, ellitsim);
    selectionData.append(Attributes.Parameter, parameter);
    selectionData.append(Attributes.Id, problemId);


    $.ajax({
        method: "POST",
        url: ServerUrls.SelectionServletUrl,
        data: selectionData.toString(),
        timeout: 2000,
        error: function (errorObject) {
            // bestSolutionErrorLabel.textContent = errorObject.responseText;
            clearErrorsLabel();
            selectionErrorLabel.innerHTML = errorObject.responseText;
            // getAndPopulateConfig();

        },
        success: function () {

            clearErrorsLabel();
            selectionErrorLabel.innerHTML = "Updated Selection Successfully";
        }
    });

    return false;



});

function deleteMutationById(mutationId) {

    const problemId = ServerUtils.getProblemIdFromUrl();

    const deleteMutationRequestData = new URLSearchParams();
    deleteMutationRequestData.append(Attributes.Id, problemId);
    deleteMutationRequestData.append(Attributes.MutationId, mutationId);
    deleteMutationRequestData.append(Attributes.Operation, Operations.Delete);

    $.ajax({
        method: "POST",
        url: ServerUrls.MutationsServletUrl,
        data: deleteMutationRequestData.toString(),
        timeout: 2000,
        error: function (errorObject) {
            // bestSolutionErrorLabel.textContent = errorObject.responseText;
            clearErrorsLabel();
            populationErrorLabel.innerHTML = errorObject.responseText;

            getAndPopulateConfig();

        },
        success: function () {

            clearErrorsLabel();
            getAndPopulateConfig();

        }
    });
}

$(populationForm).submit(function () {

    const problemId = ServerUtils.getProblemIdFromUrl();
    const population = populationTextBox.value;

    const populationData = new URLSearchParams();
    populationData.append(Attributes.Id, problemId);
    populationData.append(Attributes.Population, population);



    $.ajax({
        method: "POST",
        url: ServerUrls.PopulationServletUrl,
        data: populationData.toString(),
        timeout: 2000,
        error: function (errorObject) {
            // bestSolutionErrorLabel.textContent = errorObject.responseText;
            clearErrorsLabel();
            populationErrorLabel.innerHTML = errorObject.responseText;

            getAndPopulateConfig();

        },
        success: function () {
            clearErrorsLabel();
            populationErrorLabel.innerHTML = "Updated Population Successfully";
        }
    });

    return false;
})

function getAndPopulateConfig() {

    var userName = ServerUtils.getMyUserName();
    var problemId = ServerUtils.getProblemIdFromUrl();

    const configRequestData = new URLSearchParams();
    configRequestData.append(Attributes.Id, problemId);
    configRequestData.append(Attributes.Username, userName);

    $.ajax({
        method: "GET",
        url: ServerUrls.AlgorithmConfigServletUrl,
        data: configRequestData.toString(),
        timeout: 2000,
        error: function (errorObject) {
            // bestSolutionErrorLabel.textContent = errorObject.responseText;

        },
        success: function (algorithmConfig) {
          //  console.log(algorithmConfig);
            currentConfig = algorithmConfig;
            populateSelection(algorithmConfig.selection);
            populatePopulation(algorithmConfig.populationSize);
            populateCrossOver(algorithmConfig.crossover);
            populateMutations(algorithmConfig.mutations);
            populateStopConditions(algorithmConfig.stopConditions);


        }
    });

}

function createStopConditionRowData(stopCond) {
    const stopCondRowData = [];

    stopCondRowData.push(stopCond.name);

    const enableCheckBox = CoolInputs.createCheckBox(stopCond.enabled);
    const valueInput = CoolInputs.createTextBox(stopCond.value);

    const updateStopCondition = function () { updateStopConditionAtServer(stopCond.name, valueInput.value, enableCheckBox.checked); };
    valueInput.addEventListener("change", updateStopCondition);
    enableCheckBox.addEventListener("change", updateStopCondition);

    stopCondRowData.push(valueInput);
    stopCondRowData.push(enableCheckBox);

    return stopCondRowData;


}

function updateStopConditionAtServer(stopConditionName, value, enabled) {
    const updateStopConditionData = new URLSearchParams();

    updateStopConditionData.append(Attributes.Id, ServerUtils.getProblemIdFromUrl());
    updateStopConditionData.append(Attributes.Name, stopConditionName);
    updateStopConditionData.append(Attributes.Parameter, value);
    updateStopConditionData.append(Attributes.Enabled, enabled);



    $.ajax({
        method: "POST",
        url: ServerUrls.StopConditionsServlet,
        data: updateStopConditionData.toString(),
        timeout: 2000,
        error: function (errorObject) {

            clearErrorsLabel();
            stopConditionsErrorLabel.innerHTML = errorObject.responseText;
            getAndPopulateConfig();

        },
        success: function () {
            clearErrorsLabel();
            getAndPopulateConfig();

        }
    });

}

function populateStopConditions(stopConditions) {
    const stopConditionsTableHeaders = ["Stop Condition", "Value", "Enabled"];
    const stopConditionsTable = new CoolTable(stopConditionsTableHeaders);

    stopConditions.forEach(stopCond => {

        stopConditionsTable.addRow(createStopConditionRowData(stopCond));

    });


    stopConditionsTableDiv.innerHTML = "";
    stopConditionsTable.setTableClass("table table-dark");
    stopConditionsTableDiv.append(stopConditionsTable.getTableElement());




}

function populateMutations(mutations) {


   // console.log("mutations are:");
   // console.log(mutations);
    /*
        private String name;
    private double probality;
    private Parameter parameter;
    private String parameterString;
    private int id;
     */

    const mutationTableHeaders = ["Name", "Probality", "Paremters", "Delete"];
    const mutationTable = new CoolTable(mutationTableHeaders);
    mutationTable.setTableClass("table table-bordered table-dark");

    mutations.forEach(mutation => {
        const mutationRowData = [];
        mutationRowData.push(mutation.name);
        mutationRowData.push(mutation.probality);
        mutationRowData.push(mutation.parameterString);

        const deleteMutationButton = document.createElement("button");
        deleteMutationButton.className = "btn btn-warning";
        deleteMutationButton.innerHTML = "Delete Mutation";
        deleteMutationButton.mutationId = mutation.id;

        deleteMutationButton.addEventListener("click", function () {
            deleteMutationById(this.mutationId);

        });

        mutationRowData.push(deleteMutationButton);

        mutationTable.addRow(mutationRowData);
    });
    // console.log(mutationTable.getTableElement());

    mutationsTableDiv.innerHTML = "";
    mutationsTableDiv.append(mutationTable.getTableElement());

}

function populatePopulation(population) {

    populationTextBox.value = population;
}

function populateSelection(selection) {

    elitismTextBox.value = selection.ellitism;
    selectionComboBox.value = selection.name;


    selectionParemterTextBox.style.display = "none";
    selectionParameterNameLabel.style.display = "none";
    if (selection.parameter) {
        selectionParemterTextBox.value = selection.parameter._value;
        selectionParameterNameLabel.innerHTML = selection.parameter._name;

        selectionParemterTextBox.style.display = "block";
        selectionParameterNameLabel.style.display = "block";
    }



}

crossoverComboBox.addEventListener("change", function () {

    crossoverErrorLabel.innerHTML = "";
    aspectComboBox.style.display = "none";
    aspectNameLabel.style.display = "none";
    if (crossoverComboBox.value == CrossOverName.AspectOriented) {


        aspectComboBox.style.display = "block";
        aspectNameLabel.style.display = "block";

    }

});

function populateCrossOver(crossover) {

    crossoverComboBox.value = crossover.name;
    cuttingPointsTextBox.value = crossover.cuttingPoints;

    aspectComboBox.style.display = "none";
    aspectNameLabel.style.display = "none";
    if (crossover.name == CrossOverName.AspectOriented) {

       // console.log(crossover);
        aspectComboBox.value = crossover.parameter._value;
        aspectComboBox.style.display = "block";
        aspectNameLabel.style.display = "block";

    }

}

