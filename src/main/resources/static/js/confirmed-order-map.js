let confirmedOrderMap;
let currentRoute;

let marketIcon = L.icon({
    iconUrl: 'https://assets.mapquestapi.com/icon/v2/external/https://assets.mapquestapi.com/icon/v2/marker-md-84bf41-M.png',
    iconRetinaUrl: 'https://assets.mapquestapi.com/icon/v2/external/https://assets.mapquestapi.com/icon/v2/marker-md-84bf41-M@2x.png',
    iconSize: [35, 44],
    iconAnchor: [17, 44],
    popupAnchor: [1, -44]
});

let orderIcon = L.icon({
    iconUrl: 'https://assets.mapquestapi.com/icon/v2/external/https://assets.mapquestapi.com/icon/v2/marker-md-84bf41.png',
    iconRetinaUrl: 'https://assets.mapquestapi.com/icon/v2/external/https://assets.mapquestapi.com/icon/v2/marker-md-84bf41@2x.png',
    iconSize: [35, 44],
    iconAnchor: [17, 44],
    popupAnchor: [1, -44]
});

let locations;
let routeType;

let narrativeContainer = L.DomUtil.get('route-narrative');
let customNarrativeControl = L.control({position: 'topleft'});

let routeTypeControlContainer = L.DomUtil.get('navigation-control-for-global-map');
let routeTypeControlLayer = L.control({position: 'topright'});

function getConfirmedOrdersForMap() {
    locations  = ['вулиця 16-го Липня, 6, Рівне'];
    routeType = 'fastest';

    $.ajax({
        url: '/order/confirmed',
        type: 'GET',
        success: function (data) {
            buildMapWithOrdersLocation(data);
        },
        error: function (xhr, status, error) {
            console.error(error);
        }
    });
}

function buildMapWithOrdersLocation(data) {
    if (confirmedOrderMap !== undefined){
        confirmedOrderMap.remove();
    }

    confirmedOrderMap = L.map('confirmed-order-map', {
        center: [50.61874487347395, 26.2488085293568],
        layers: MQ.mapLayer(),
        zoom: 12,
        zoomControl: false
    });

    L.control.zoom({
        position: 'topright'
    }).addTo(confirmedOrderMap);

    MQ.geocode().search('вулиця 16-го Липня, 6, Рівне').on('success', function(e) {
        let best = e.result.best,
            latlng = best.latlng;

        let marker = L.marker([ latlng.lat, latlng.lng ], {icon: marketIcon})
            .addTo(confirmedOrderMap)
            .bindPopup('<strong>Needler\'s Market</strong>');

        marker.on('mouseover', function () {
            this.openPopup();
        });

        marker.on('mouseout', function () {
            this.closePopup();
        });
    });

    $.each(data, function (index, order) {

        MQ.geocode().search(order.address).on('success', function(e) {
            let best = e.result.best,
                latlng = best.latlng;

            let marker = L.marker([ latlng.lat, latlng.lng ], {icon: orderIcon})
                .addTo(confirmedOrderMap)
                .bindPopup('<strong> Order №' + order.id + '</strong>');

            marker.on('mouseover', function () {
                this.openPopup();
            });

            marker.on('mouseout', function () {
                this.closePopup();
            });

            marker.on('click', function () {
                if (locations.includes(order.address)){

                    const index = locations.indexOf(order.address);
                    if (index > -1) {
                        locations.splice(index, 1);
                    }

                    if (locations.length > 1){
                        buildGlobalRoute();
                    } else {
                        L.DomUtil.get('route-narrative').innerHTML = '';
                        confirmedOrderMap.removeLayer(currentRoute);
                    }

                } else {
                    locations.splice(locations.length, 0, order.address)
                    buildGlobalRoute();
                }
            })
        });

    });

    customNarrativeControl.onAdd = function(confirmedOrderMap) {
        return narrativeContainer;
    }

    customNarrativeControl.addTo(confirmedOrderMap);

    routeTypeControlLayer.onAdd = function(confirmedOrderMap) {
        return routeTypeControlContainer;
    }

    routeTypeControlLayer.addTo(confirmedOrderMap);

    $('#route-narrative').on('mouseover', function () {
       confirmedOrderMap.scrollWheelZoom.disable();
    });

    $('#route-narrative').on('mouseout', function () {
        confirmedOrderMap.scrollWheelZoom.enable();
    });

    L.DomUtil.get('route-narrative').innerHTML = '';

    $('.fastestForGlobalMap').prop('disabled', true);

    $('.fastestForGlobalMap').on('click', function () {
        changeRouteType($(this));
    });

    $('.shortestForGlobalMap').on('click', function () {
        changeRouteType($(this));
    });

    $('.bicycleForGlobalMap').on('click', function () {
        changeRouteType($(this));
    });
}

function changeRouteType(link){
    if (currentRoute !== undefined){
        link.siblings('button').prop('disabled', false);

        routeType = link.attr('routeType');
        buildGlobalRoute();

        link.prop('disabled', true);
    }
}

function buildGlobalRoute() {
    if (currentRoute !== undefined){
        confirmedOrderMap.removeLayer(currentRoute);
    }

    let dir = MQ.routing.directions()
        .on('success', function(data) {
            let routeNarrative = data.route.legs;

            if (routeNarrative && routeNarrative.length){

                let globalContainer = $('<div>');
                let routeSummary = $('<div class="route-summary">');
                let routeTime = $('<span class="route-time-summary">');
                let routeDistance = $('<span class="route-distance-summary">');

                routeTime.appendTo(routeSummary);
                routeDistance.appendTo(routeSummary);

                routeTime.text('Приблизно ' + Math.round( (data.route.realTime + (locations.length - 1) * 120 )/60 ) + ' хв.')
                routeDistance.text('' + data.route.distance.toFixed(1) + ' км.')

                routeSummary.appendTo(globalContainer);

                let counter = 1;

                for (let i = 0; i < routeNarrative.length; i++){

                    let maneuvers = routeNarrative[i].maneuvers;

                    for (let j = 0; j < maneuvers.length; j++){

                        let maneuverContainer;

                        if (j === maneuvers.length - 1){
                            if (i === routeNarrative.length - 1){
                                maneuverContainer = $('<div class="maneuver-container destination last">');
                            } else {
                                maneuverContainer = $('<div class="maneuver-container destination">');
                            }
                        } else {
                            maneuverContainer = $('<div class="maneuver-container">');
                        }

                        let maneuverCounter = $('<span class="maneuver-counter">');

                        let maneuver = $('<span class="maneuver-title">');

                        let maneuverDistance = $('<span class="maneuver-distance">');

                        maneuverCounter.appendTo(maneuverContainer);
                        maneuver.appendTo(maneuverContainer);
                        maneuverDistance.appendTo(maneuverContainer);

                        maneuverCounter.text('' + counter++ + '.');
                        maneuver.text('' + maneuvers[j].narrative.substring(0, maneuvers[j].narrative.indexOf('.') + 1));
                        maneuverDistance.text('' + maneuvers[j].narrative.substring(maneuvers[j].narrative.indexOf('.') + 11));

                        maneuverContainer.appendTo(globalContainer);
                    }

                }

                L.DomUtil.get('route-narrative').innerHTML = globalContainer.html();

            }
    });

    dir.route({
        locations: locations,
        options: {
            routeType: routeType,
            unit: 'k',
            locale: 'uk_UA',
            useTraffic: true
        }
    });

    currentRoute = MQ.routing.routeLayer({
        directions: dir,
        fitBounds: true,
        draggable: false
    });

    confirmedOrderMap.addLayer(currentRoute);
}