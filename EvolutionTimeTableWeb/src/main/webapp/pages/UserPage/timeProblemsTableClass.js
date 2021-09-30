class UsersTimeProblemsTable {
    constructor(tableDiv) {

        tableDiv.innerHTML = "";

        const timeProblemsTableHeaders = ["Threads Alive Solving", "D", "H", "T", "C", "S", "hard rules", "soft rules", "owner", "best fitness", "View", "Solve", "Status"];
        this.timeProblemsTable = document.createElement("table");
        const thead = document.createElement("thead");
        const threadTr = document.createElement("tr");

        timeProblemsTableHeaders.forEach(header => {
            threadTr.innerHTML += `<th> ${header} </th>`;
        });

        thead.append(threadTr);
        this.timeProblemsTable.className = "rtable";
        this.timeProblemsTable.append(thead);
        this.tbody = document.createElement("tbody");
        this.timeProblemsTable.append(this.tbody);
        this.timeProblemsTable.id = "timeProblemsTable";
        this.rowsMap = new Map();

        tableDiv.append(this.timeProblemsTable);

    }

    addOrUpdateTimeProblemRow(timeProblemDTO) {

        this.addTimeProblemRowIfNotExists(timeProblemDTO);
        this.updateTimeProblemRow(timeProblemDTO);

    }

    populateOrUpdateRows(timeProblemsDTO) {
        timeProblemsDTO.forEach(timeProblemDTO => {
            this.addOrUpdateTimeProblemRow(timeProblemDTO);
        });
    }

    updateTimeProblemRow(timeProblemDTO) {

        const timeProblemRowTDS = this.rowsMap.get(timeProblemDTO.id);
        
        timeProblemRowTDS.bestFitnessTD.innerHTML = timeProblemDTO.bestFitness;
        timeProblemRowTDS.howManySolvingTD.innerHTML = timeProblemDTO.howManySolving;
        timeProblemRowTDS.algorithmStateTD.innerHTML = timeProblemDTO.algorithmState;
    }

   createTD(data)
   {
     const td = document.createElement("td");
     td.innerHTML = data;
     return td;
   }

    addTimeProblemRowIfNotExists(timeProblemDTO) {
        const timeProblemId = timeProblemDTO.id;
        if (!this.rowsMap.get(timeProblemId)) {
            const timeProblemRow = document.createElement("tr");
           
          
            
          
             const howManySolvingTD = document.createElement("td");
            howManySolvingTD.innerHTML = timeProblemDTO.howManySolving;
            timeProblemRow.append(howManySolvingTD);

            timeProblemRow.append(this.createTD(timeProblemDTO.days));
            timeProblemRow.append (this.createTD(timeProblemDTO.hours));
            timeProblemRow.append (this.createTD(timeProblemDTO.teachers));
            timeProblemRow.append (this.createTD(timeProblemDTO.classes));
            timeProblemRow.append (this.createTD(timeProblemDTO.subjects));
            timeProblemRow.append  (this.createTD(timeProblemDTO.hardRules));
            timeProblemRow.append  (this.createTD(timeProblemDTO.softRules));
            timeProblemRow.append (this.createTD(timeProblemDTO.owner));
             
          
            const bestFitnessTD = document.createElement("td");
            bestFitnessTD.innerHTML = timeProblemDTO.bestFitness;
            timeProblemRow.append(bestFitnessTD);

            const viewButton = document.createElement("button");
            const solveButton = document.createElement("button");

            solveButton.textContent = "Solve Me!!!";
            solveButton.addEventListener("click", function () {
                goToAlgorithmOperationPage(timeProblemDTO.id);

            });

            viewButton.textContent = "View Me!!!";
            viewButton.addEventListener("click", function () {
                goToViewProblemPage(timeProblemDTO.id);
            })

            viewButton.className = "btn btn-info";
            solveButton.className = "btn btn-primary";
            
            const viewButtonTD = document.createElement("td");
            const solveButtonTD = document.createElement("td");
            
            viewButtonTD.append(viewButton);
            solveButtonTD.append(solveButton);

            timeProblemRow.append(viewButtonTD);
            timeProblemRow.append(solveButtonTD);

            const algorithmStateTD = document.createElement("td");
            algorithmStateTD.innerHTML = timeProblemDTO.algorithmState;

            timeProblemRow.append(algorithmStateTD);
            this.tbody.append(timeProblemRow);
            
           
            
            this.rowsMap.set(timeProblemDTO.id,
                {
                    "howManySolvingTD": howManySolvingTD,
                    "bestFitnessTD": bestFitnessTD,
                    "algorithmStateTD": algorithmStateTD

                });
                
               

        }
    }




}
