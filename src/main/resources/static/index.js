const apiAddress = 'http://localhost:8080/api/employee';

const getAllEmployees = () => {
    fetch(`${apiAddress}/all`)
        .then(res => {
            if(!res.ok) {
                alert(`HTTP responce error: ${res.status}`);
            }
            return res.json();
        })
        .then(data => {
            // TODO employees' cards
            document.querySelector('.employees__list').innerText = JSON.stringify(data);
        });

}

const getEmployee = () => {
    let url = `${apiAddress}/${document.querySelector('#get__id').value}`;
    fetch(url)
        .then(res => {
            if(!res.ok) {
                alert(`HTTP responce error: ${res.status}`);
            }
            return res.json();
        })
        .then(data => {
            // TODO employees' cards
            document.querySelector('#gotten__employee').innerText = JSON.stringify(data);
        });
}

const postEmployee = () => {
    let obj = {
        firstName: document.querySelector('#add__firstname').value,
        lastName: document.querySelector('#add__lastname').value,
        email: document.querySelector('#add__email').value,
        salary: document.querySelector('#add__salary').value
    }

    fetch(apiAddress, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(obj)
    })
    .then(res => {
        if (!res.ok) {
            alert(`HTTP responce error: ${res.status}`);
        }
        getAllEmployees();
    });
}

const putEmployee = () => {
    let obj = {
        id: document.querySelector('#put__id').value,
        firstName: document.querySelector('#put__firstname').value,
        lastName: document.querySelector('#put__lastname').value,
        email: document.querySelector('#put__email').value,
        salary: document.querySelector('#put__salary').value
    }

    fetch(apiAddress, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(obj)
    })
    .then(res => {
        if (!res.ok) {
            alert(`HTTP responce error: ${res.status}`);
        }
        getAllEmployees();
    });
}

const deleteEmployee = () => {
    let url = `${apiAddress}/${document.querySelector('#delete__id').value}`

    fetch(url, {
        method: 'DELETE'
    })
    .then(res => {
        if (!res.ok) {
            alert(`HTTP responce error: ${res.status}`);
        }
        getAllEmployees();
    });
}

getAllEmployees();
document.querySelector('#get__submit').addEventListener('click', getEmployee);
document.querySelector('#add__submit').addEventListener('click', postEmployee);
document.querySelector('#put__submit').addEventListener('click', putEmployee);
document.querySelector('#delete__submit').addEventListener('click', deleteEmployee);