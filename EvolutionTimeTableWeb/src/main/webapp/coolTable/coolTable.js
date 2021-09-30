
const testDiv = document.querySelector("#test");
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





    addRow(rowData) {

        const tableRow = document.createElement("tr");
        rowData.forEach(data => {
            tableRow.innerHTML += `<td> ${data} </td>`;
        });

        this.tbody.append(tableRow);

    }

    getTableElement() {
        return this.tableElement;
    }
};

$(function () {

/*
    console.log("testing some stuff dude");
    const headers = ["id", "name"];
    const myTable = new CoolTable(headers);
    myTable.addRow(["1", "or"]);
    console.log(myTable.getTableElement());
    testDiv.append(myTable.getTableElement()); */

})




