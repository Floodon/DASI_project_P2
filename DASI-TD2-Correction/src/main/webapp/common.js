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

// ------------
// Transformateurs : Objet --> HTML
/** @param {Medium} m */
const mediumToHtml = m => `${m.name} (${m.type})`

/** @param {Medium[]} mediums */
const mediumsToHtml = mediums => ul(mediums.map(m => li(mediumToHtml(m))).join(''))

// Quelques types utiles
/** @typedef {{name: string, type: string}} Medium */
