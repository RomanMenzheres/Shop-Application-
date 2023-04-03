$(document).ready(function () {
    let map = L.map('map').setView([50.61874487347395, 26.2488085293568], 16);
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: 'Map data © <a href="https://openstreetmap.org">OpenStreetMap</a> contributors',
        maxZoom: 18,
    }).addTo(map);

    let shopIcon = L.icon({
        iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-red.png',
        shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
        iconSize: [25, 41],
        iconAnchor: [12, 41],
        popupAnchor: [1, -34],
        shadowSize: [41, 41]
    });

    let storeMarker = L.marker([50.61874487347395, 26.2488085293568], {icon: shopIcon}).addTo(map);
    storeMarker.bindPopup("<h2>Needler's fresh market</h2>").openPopup();

    L.Circle.include({
        contains: function (latLng) {
            return this.getLatLng().distanceTo(latLng) < this.getRadius();
        }
    });

    let availableArea = L.circle([50.61874487347395, 26.2488085293568], {
        color: "#84BF41",
        radius: 1600,
        fillOpacity: 0.3,
        fillColor: "#84BF41"
    }).addTo(map);

    let userMarker;

    map.on('click', function (e) {
        let latlng = e.latlng;

        if(availableArea.contains(latlng)) {
            if (userMarker) {
                map.removeLayer(userMarker);
            }

            let address;

            let geocodeServiceUrl = 'https://nominatim.openstreetmap.org/reverse?format=json&lat=' + latlng.lat + '&lon=' + latlng.lng;

            fetch(geocodeServiceUrl)
                .then(function (response) {
                    return response.json();
                })
                .then(function (json) {
                    address = json.display_name;

                    address = address.substring(0, address.indexOf("Рівне") +5);

                    $(".address-input").val(address);
                });

            userMarker = L.marker(latlng).addTo(map);
        }
    });

});