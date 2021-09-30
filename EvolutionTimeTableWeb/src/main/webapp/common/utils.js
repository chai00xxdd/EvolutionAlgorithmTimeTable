

function createTable(headersData, rowsData) {
    const newTable = document.createElement("table");
    newTable.append(createTableHeaders(headersData));
    rowsData.forEach(rowData => {
        newTable.append(createTableRow(rowData));
    });

    return newTable;
}

function createTableHeaders(headersData) {
    const headersRow = document.createElement("tr");
    headersData.forEach(cellData => {
        headersRow.innerHTML += `<th> ${cellData} </th>`;
    });
    return headersRow;
}
function createTableRow(rowData) {
    const newRow = document.createElement("tr");
    rowData.forEach(cellData => {
        newRow.innerHTML += `<td> ${cellData} </td>`;
    });
    return newRow;
}

function createTableData(data) {
    return `<td> ${data} </td>`;
}

function getCookie(cname) {
    let name = cname + "=";
    let decodedCookie = decodeURIComponent(document.cookie);
    let ca = decodedCookie.split(';');
    for (let i = 0; i < ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

function getCurrentUrl() {
    return window.location.href;
}

function getParemterFromUrl(paremter) {
    const url = new URL(getCurrentUrl());
    return url.searchParams.get(paremter);
}

function createParemterData(paremter, value) {
    return paremter + "=" + value;
}

function getMyUserName() {
    return getCookie("username");
}



class AlgorithmConfigGuiAdapter {
    constructor(algorithmConfigDTO) {
        this.config = algorithmConfigDTO;
    }


    createConfigTable() {
        const configTableHeaders = ["attribute", "configuration"];
        const configTable = new CoolTable(configTableHeaders);
        const population = this.config.populationSize;
        const selection = this.config.selection;
        const crossover = this.config.crossover;

        let selectionConfig = `Ellitism = ${selection.ellitism}`;
        if (selection.parameterString && selection.parameterString != "") {
            selectionConfig += ", " + selection.parameterString;
        }

        let crossOverConfig = `CuttingPoints = ${crossover.cuttingPoints}`;
        if (crossover.paremterString && crossover.paremterString != "") {
            crossOverConfig += ", " + crossover.paremterString;
        }

        configTable.addRow(["population", "size = " + population]);
        configTable.addRow([selection.name + " Selection", selectionConfig])
        configTable.addRow([crossover.name + " Crossover", crossOverConfig]);

        return configTable.getTableElement();

    }

    createMutationsTable() {

        const mutationsTableHeaders = ["Name", "Probality", "Configuration"];
        const mutatoinsTable = new CoolTable(mutationsTableHeaders);
        const mutations = this.config.mutations;

        mutations.forEach(mutation => {
            mutatoinsTable.addRow([mutation.name, mutation.probality, mutation.parameterString]);
        });

        return mutatoinsTable.getTableElement();

    }
}


class ServerUtils {
    static getMyUserName() {
        return getMyUserName();
    }

    static getProblemIdFromUrl() {
        return getParemterFromUrl(Attributes.Id);
    }
}

class Attributes {

    static Username = "username";
    static Id = "id";
    static Message = "message";
    static Operation = "operation";
    static Graph = "graph";
    static Population = "population";
    static Parameter = "parameter";
    static Name = "name";
    static Probality = "probality";
    static Elittism = "ellitism";
    static CuttingPoints = "cuttingPoints";
    static MutationId = "mutationId";
    static Component = "component";
    static Enabled = "enabled";

}

class Operations {
    static Add = "add";
    static Delete = "delete";
    static Run = "run";
    static Stop = "stop";
    static Pause = "pause";
    static Resume = "resume";
}


class ServerUrls {

    static AlgorithmConfigServletUrl = "/EvolutionTimeTableWeb/AlgorithmConfuigrationServlet";
    static PopulationServletUrl = "/EvolutionTimeTableWeb/PopulationServlet";
    static SelectionServletUrl = "/EvolutionTimeTableWeb/SelectionServlet";
    static CrossOverServletUrl = "/EvolutionTimeTableWeb/CrossOverServlet";
    static MutationsServletUrl = "/EvolutionTimeTableWeb/MutationsServlet";
    static StopConditionsServlet = "/EvolutionTimeTableWeb/StopConditionsServlet";
    static UsersServletUrl = "/EvolutionTimeTableWeb/GetUsersServlet";
    static TimeProblemsShortDTOServletUrl = "/EvolutionTimeTableWeb/GetTimeProblemsShortServlet";
    static TimeProblemServletUrl = "/EvolutionTimeTableWeb/GetTimeProblemDetails";
    static UsersAlgorithmProgressServletUrl = "/EvolutionTimeTableWeb/AlgorithmProgressOfAllServlet";
    static BestSolutionServletUrl = "/EvolutionTimeTableWeb/GetBestSolutionServlet";
    static AlgorithmOperationServletUrl = "/EvolutionTimeTableWeb/AlgorithmOperationServlet";

}

