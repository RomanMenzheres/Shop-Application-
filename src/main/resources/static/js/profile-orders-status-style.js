$(document).ready(function () {

    let listOfStatus = $('.top-status');

    for (let i = 0; i < listOfStatus.length; i++){
        let status = listOfStatus[i];

        console.log(status.children[0])
        console.log(status.children[0].innerText)
        console.log(status.children[0].text === 'DELIVERED')

        if (status.children[0].innerText === 'PROCESSING'){

            status.classList.add("processing");

        } else if (status.children[0].innerText === 'PAID'){

            status.classList.add("paid");

        } else if (status.children[0].innerText === 'CONFIRMED'){

            status.classList.add("confirmed");

        } else if (status.children[0].innerText === 'DELIVERED'){

            status.classList.add("delivered");

        } else if (status.children[0].innerText === 'CANCELED'){

            status.classList.add("canceled");

        }
    }

});