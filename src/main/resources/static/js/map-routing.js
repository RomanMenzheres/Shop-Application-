let map;
let from = 'вулиця 16-го Липня, 6, Рівне';
let myContainer = L.DomUtil.get('navigation-control-for-open-order');
let customControl = L.control({position: 'topleft'});

CustomRouteLayer = MQ.Routing.RouteLayer.extend({
    createStopMarker: function (location, stopNumber) {
        var custom_icon,
            marker;

        custom_icon = L.icon({
            iconUrl: 'https://assets.mapquestapi.com/icon/v2/external/https://assets.mapquestapi.com/icon/v2/marker-' + stopNumber + '.png',
            iconRetinaUrl: 'https://assets.mapquestapi.com/icon/v2/external/https://assets.mapquestapi.com/icon/v2/marker-' + stopNumber + '@2x.png',
            iconSize: [25, 31],
            iconAnchor: [12, 31],
            popupAnchor: [1, -31]
        });

        marker = L.marker(location.latLng, {
            icon: custom_icon,
            draggable: this.options.draggable
        }).addTo(map);

        return marker;
    }
});

function buildRoute(link, orderLocation){
    if (map !== undefined) {
        map.remove();
    }

    map = L.map('map', {
        center: [50.61874487347395, 26.2488085293568],
        layers: MQ.mapLayer(),
        zoom: 12
    });

    link.siblings('button').prop('disabled', false);

    var directions = MQ.routing.directions().on('success', function (data) {
        if (data.info !== undefined){
            $('.route-distance-info').text('Відстань: ' + data.route.distance + ' км');
            $('.route-time-info').text('Час у дорозі: ' + data.route.legs[0].formattedTime);
        }
    });

    directions.route({
        locations: [
            from,
            orderLocation
        ],
        options: {
            routeType: link.attr('class'),
            unit: 'k',
            avoidTimedConditions: true,
            useTraffic: true
        }
    });

    let directionsLayer = new CustomRouteLayer({
        directions: directions,
        fitBounds: true,
        draggable: false
    })

    map.addLayer(directionsLayer);

    customControl.onAdd = function(map) {
        return myContainer;
    }

    customControl.addTo(map);

    link.prop('disabled', true);
}
