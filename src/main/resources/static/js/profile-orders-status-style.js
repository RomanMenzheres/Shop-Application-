$(document).ready(function () {

    let listOfStatus = $('.top-status');

    for (let i = 0; i < listOfStatus.length; i++){
        let status = listOfStatus[i];

        if (status.children[0].innerText === 'PROCESSING'){

            status.children[0].innerText = 'ОБРОБЛЯЄТЬСЯ';
            status.classList.add("processing");

        } else if (status.children[0].innerText === 'PAID'){

            status.children[0].innerText = 'СПЛАЧЕНО';
            status.classList.add("paid");

        } else if (status.children[0].innerText === 'CONFIRMED'){

            status.children[0].innerText = 'ПІДТВЕРДЖЕНО';
            status.classList.add("confirmed");

        } else if (status.children[0].innerText === 'DELIVERED'){

            status.children[0].innerText = 'ДОСТАВЛЕНО';
            status.classList.add("delivered");

        } else if (status.children[0].innerText === 'CANCELED'){

            status.children[0].innerText = 'СКАСОВАНО';
            status.classList.add("canceled");

        }
    }

});