function addUser() {
    var allUsersSelect = document.getElementById('allUsers');
    var testId = document.getElementsByName('testId')[0].value;
    var chosenOption = allUsersSelect.selectedOptions[0];
    $.ajax({
        url: "http://localhost:8080/tester/createAccess",
        data: {
            testId : +testId,
            userId : +chosenOption.value
        },
        success: function () {
            fillAccessedUsersSelect();
        }
    });
}

function removeFromAccessed() {
    var testId = document.getElementsByName('testId')[0].value;
    var accessedUsersSelect = document.getElementById('accessedUsers');
    var chosenUsers = accessedUsersSelect.selectedOptions;
    $.ajax({
        url: "http://localhost:8080/tester/removeAccess",
        data: {
            testId : +testId,
            userId : +chosenUsers[0].value
        },
        success: function () {
            fillAccessedUsersSelect();
        }
    });
}

function fillAccessedUsersSelect() {
    var testId = document.getElementsByName('testId')[0].value;
    var accessedUsersSelect = document.getElementById('accessedUsers');
    for (var i=0; i<accessedUsersSelect.children.length;) {
        console.log(accessedUsersSelect.children[i]);
        accessedUsersSelect.removeChild(accessedUsersSelect.children[i]);
    }
    $.ajax({
        url: "http://localhost:8080/tester/getAccessed",
        data: {
            testId : +testId
        },
        success: function (data) {
            console.log(data);
            data.forEach(function(user) {
                var userOption = document.createElement('option');
                userOption.value = user.id;
                userOption.text = user.username;
                accessedUsersSelect.appendChild(userOption);
            })
        }
    });
}



