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

const reformatDateTimeString = (dts) => {
    // dts = date time string, 'YYYY-MM-DD HH:MM:SS' format
    const [date, time] = dts.split(' ')
    const [Y, M, D] = date.split('-')
    const [h, m, s] = time.split(':')
    const p = s => (s || '??').padStart('0', 2)
    return `${p(D)}/${p(M)}/${Y} ${p(h)}h${p(m)}`
}

// ------------
// Transformateurs : Objet --> HTML
/** @param {Medium} m */
const mediumToHtml = m => `${m.prenom} ${m.nom} (${m.type})`

/** @param {Medium[]} mediums */
const mediumsToHtml = mediums => ul(mediums.map(m => li(mediumToHtml(m))).join(''))

/** @param {Consultation} consult */
const consultationToTr = consult => {
    const fields = [
        reformatDateTimeString(consult.dateDebut),
        reformatDateTimeString(consult.dateFin),
        mediumToHtml(consult.medium)
    ]
    return wrap('tr', fields.map(x => wrap('td', x)).join('\n'))
}

// Quelques types utiles
/** @typedef {{name: string, type: string}} Medium */
/** @typedef {{dateDebut: string, dateFin: string, medium: Medium}} Consultation */
