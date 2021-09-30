const headers = ["name", "last name", "salary"];
const rows = [["or", "lan", "533453"]]; //["orsad", "asdasd", "533433453"]
console.log(rows.length);

const table = createTable(headers, rows);
document.body.append(table);
