class UsersProblemsProgressTable {
    constructor(userProgressTable) {
        this.userProgressTable = userProgressTable;

        const usersProgressTableHeaders = ["User", "Generation", "Best Fitness", "Best Solution", "Algorithm Config"];
        const thead = document.createElement("thead");
        const threadTr = document.createElement("tr");
        this.tbody = document.createElement("tbody");

        usersProgressTableHeaders.forEach(header => {
            threadTr.innerHTML += `<th> ${header} </th>`;
        });

        thead.append(threadTr);
        this.userProgressTable.append(thead);
        this.userProgressTable.append(this.tbody);

        this.rowsMap = new Map();

    }

    populateOrUpdateUsersProgress(usersProgressDTO) {
        usersProgressDTO.forEach(userProgress => this.addOrUpdateTimeProblemRow(userProgress));
    }

    createTD(data) {
        const td = document.createElement("td");
        td.innerHTML = data;
        return td;
    }
    
    createComponentTD(component)
    {
     const td = document.createElement("td");
        td.append(component);
        return td;
    }

    addOrUpdateTimeProblemRow(userProgressDTO) {

        this.addUserProgressRowIfNotExists(userProgressDTO);
        this.updateUserProgressRow(userProgressDTO);

    }
    updateUserProgressRow(userProgressDTO) {
        const progress = userProgressDTO.algorithmProgress;
        const userProressRowTDS = this.rowsMap.get(userProgressDTO.userName);
        userProressRowTDS.fitnessTD.innerHTML = progress.fitness;
        userProressRowTDS.generationTD.innerHTML = progress.generation;
    }

    addUserProgressRowIfNotExists(userProgress) {

        const userName = userProgress.userName;
        if (!this.rowsMap.get(userName)) {

            const userProgressRow = document.createElement("tr");
            const progress = userProgress.algorithmProgress;

            const generationTD = this.createTD(progress.generation);
            const fitnessTD = this.createTD(progress.fitness);

            userProgressRow.append(this.createTD(userProgress.userName));
            userProgressRow.append(generationTD);
            userProgressRow.append(fitnessTD);

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

                getAndPopulateBestSolution(userName, problemId);

            }); //button eventListener Ends

            if (progress.fitness == 0) {
                showBestSolutionButton.disabled = true;
                //showConfigButton.disabled = true;
            }

            userProgressRow.append(this.createComponentTD(showBestSolutionButton));
            userProgressRow.append(this.createComponentTD(showConfigButton));

            this.tbody.append(userProgressRow);

            this.rowsMap.set(userName, {
                "fitnessTD": fitnessTD,
                "generationTD": generationTD

            });
        }

    }

}