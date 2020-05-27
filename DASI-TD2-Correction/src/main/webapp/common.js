/* eslint-disable no-unused-vars */
// Prédict'IF

// ------------
// Fonctions utilitaires
function createElementFromHTML(html) {
    const div = document.createElement('div')
    div.innerHTML = html.trim()
    return div.firstChild
}

// ------------
// Fonction pour les requêtes
function requestApi(params = {}) {
    console.log(params)

    const searchParams = Object.keys(params).map((key) => {
        return encodeURIComponent(key) + '=' + encodeURIComponent(params[key])
    }).join('&')

    return fetch('./ActionServlet', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
        },
        body: searchParams
    }).then(x => x.json())
}

/**
 * @param {string} tag
 * @param {string} inner
 */
const wrap = (tag, inner) => `<${tag}>${inner}</${tag}>`
const ul = inner => wrap('ul', inner)
const li = inner => wrap('li', inner)
const tr = inner => wrap('tr', inner)
const td = inner => wrap('td', inner)

const reformatDateTimeString = (dts) => {
    // dts = date time string, 'YYYY-MM-DD HH:MM:SS' format
    const [date, time] = dts.split(' ')
    const [Y, M, D] = date.split('-')
    const [h, m, s] = time.split(':')
    const p = X => (X || '??').padStart('0', 2)
    return `${p(D)}/${p(M)}/${Y} ${p(h)}h${p(m)}`
}

const reformatDateString = (ds) => {
    // ds = date string, 'YYYY-MM-DD' format
    const [Y, M, D] = ds.split('-')
    const p = X => (X || '??').padStart('0', 2)
    return `${p(D)}/${p(M)}/${Y}`
}

// ------------
// Transformateurs : Objet --> HTML
/** @param {Medium} m */
const mediumToHtml = m => `${m.denomination} (${m.type})`

const clientToHtml = c => `${c.prenom} ${c.nom}`

/** @param {Medium[]} mediums */
const mediumsToHtml = mediums => ul(mediums.map(m => li(mediumToHtml(m))).join(''))

/** @param {Consultation} consult */
const consultationToTr = consult => {
    const fields = [
        reformatDateTimeString(consult.dateDebut),
        reformatDateTimeString(consult.dateFin),
        mediumToHtml(consult.medium)
    ]
    return tr(fields.map(x => td(x)).join('\n'))
}

/** @param {Consultation} consult */
const consultationToTrExtended = consult => {
    const fields = [
        reformatDateTimeString(consult.dateDebut),
        reformatDateTimeString(consult.dateFin),
        mediumToHtml(consult.medium),
        clientToHtml(consult.client),
        consult.commentaire
    ]
    return tr(fields.map(x => td(x)).join('\n'))
}

// Quelques types utiles
/** @typedef {{denomination: string, type: string}} Medium */
/** @typedef {{nom: string, prenom: string}} Client */
/** @typedef {{dateDebut: string, dateFin: string, medium: Medium, client: Client, commentaire: string}} Consultation */


/* Exécuté */
function logout() {
    return requestApi({todo: 'deconnexion'})
        .then(() => {
            window.location.assign('./login.html')
        })
        .catch(e => {
            console.error('fetch error', e)
        })
}
const logoutButton = document.getElementById('logout-button')
if (logoutButton) {
    logoutButton.addEventListener('click', logout)
}


/**
 * @param {string[]} routes
 * @param {string} initialRoute
 */
const Router = (routes, initialRoute = '__NONE__', callback = null) => {
    const getRouteDiv = (r) => document.getElementById('route-' + r)

    let currentRoute = '__NONE__'
    const setRoute = (newRoute) => {
        console.log(`Router: ${currentRoute} --> ${newRoute}`)

        currentRoute = newRoute

        routes.filter(x => x !== newRoute).forEach(rt => {
            const el = getRouteDiv(rt)
            if (el) {
                el.setAttribute('hidden', 'hidden')
            }
        })

        const el = getRouteDiv(newRoute)
        if (el) {
            el.removeAttribute('hidden')
        }

        if (callback) {
            callback(newRoute, el)
        }
    }

    setRoute(initialRoute)

    const missingRoutes = routes.filter(rt => !getRouteDiv(rt))
    if (missingRoutes.length > 0) {
        console.error('Missing routes:', missingRoutes.join(', '))
    }

    return { setRoute, getRouteDiv }
}

function updatePlaceholder(key, value, property = 'innerText') {
    const els = document.querySelectorAll(`[data-key="${key}"]`)
    els.forEach(x => {
        x[property] = value
    })
}

function prettyIndex(index) {
    const i = index + 1

    if (i === 1) {
        return '1er 🏆'
    } else if (i === 2) {
        return '2e 🥈'
    } else if (i === 3) {
        return '3e 🥉'
    }

    return i.toString() + 'e'
}
