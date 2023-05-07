let map;
let from = 'вулиця 16-го Липня, 6, Рівне';
let myContainer = L.DomUtil.get('navigation-control-for-open-order');
let customControl = L.control({position: 'topleft'});

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
            $('.route-distance-info').text('Distance: ' + data.route.distance + ' km');
            $('.route-time-info').text('Time: ' + data.route.legs[0].formattedTime);
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
            locale: 'uk_UA',
            avoidTimedConditions: true,
            useTraffic: true
        }
    });

    let directionsLayer = MQ.routing.routeLayer({
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
