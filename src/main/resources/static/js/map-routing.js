let map;

function openMap(orderLocation) {

    if (map !== undefined) {
        map.remove();
    }

    if (map !== undefined){
        map.remove();
    }

    map = L.map('map', {
        layers: MQ.mapLayer(),
        center: [50.61874487347395, 26.2488085293568],
        zoom: 12
    });

    let direction = MQ.routing.directions();

    direction.route({
        locations: [
            'вулиця 16-го Липня, 6, Рівне',
            orderLocation
        ],

        options: {
            unit: 'm',
            routeType: 'shortest',
            avoidTimedConditions: true
        }
    });

    MainRouteLayer = MQ.Routing.RouteLayer.extend({
        initStartMarker: (location) => {
            let startIcon;
            let startMarker;

            startIcon = L.icon({
                iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-red.png',
                shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
                iconSize: [25, 41],
                iconAnchor: [12, 41],
                popupAnchor: [1, -34],
                shadowSize: [41, 41]
            });

            startMarker = L.marker(location.latlng, {icon: startIcon}).addTo(map);

            return startMarker;
        },

        initEndmarker: (location) => {
            let endIcon;
            let endMarker;

            endIcon = L.icon({
                iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-blue.png',
                shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
                iconSize: [25, 41],
                iconAnchor: [12, 41],
                popupAnchor: [1, -34],
                shadowSize: [41, 41]
            });

            endMarker = L.marker(location.latlng, {icon: endIcon}).addTo(map);

            return endMarker;
        }
    });

    map.addLayer(new MainRouteLayer({
        directions: direction,
        fitBounds: true
    }));
}
