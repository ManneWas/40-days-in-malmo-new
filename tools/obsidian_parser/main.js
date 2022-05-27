let fs = require('fs');
fs.readdir('resources/', (err, filenames) => {
    if (err) {
        console.error(err)
    }

    function prepend(value, array) {
        let newArray = array.slice();
        newArray.unshift(value);
        return newArray;
    }

    filenames.forEach(function (filename) {
        if (filename.includes(".md")) {
            fs.readFile('resources/' + filename, 'utf-8', function (err, content) {
                if (err) {
                    console.error(err)
                }
                content = content.trim()
                let lines = content.split(/\r?\n/);
                let result = ""
                lines = prepend(`{"name":"${filename.replace(".md", "")}"`, lines)
                for (let i = 1; i < lines.length; i++) {
                    lines[i] = lines[i].replace('[[', '"')
                    lines[i] = lines[i].replace(']]', '"')
                    lines[i] = lines[i].replace(/([^"](":)$)/, '$1[')

                    if (lines[i - 1].includes('- ') && !lines[i].includes('- ')) {
                        lines[i - 1] = lines[i - 1] + ']'
                    } else if (lines[i].includes('- ') && i === lines.length - 1) {
                        lines[i] = lines[i] + ']'
                    }
                    lines[i] = lines[i].replace(']]', '"')

                }
                for (let i = 0; i < lines.length; i++) {
                    lines[i] = lines[i].replace('- ', '"name":')


                    if (i < lines.length - 1) {
                        lines[i] = lines[i].replace(/([^"]("$))/, '$1,')
                        if (!lines[i].includes(',')) {
                            lines[i] = lines[i] + ','
                            lines[i] = lines[i].replace('[,', '[')
                        }
                    }
                    if (i > 0) {
                        if (lines[i].includes('"name"')) {
                            lines[i] = '{' + lines[i]
                            lines[i] = lines[i].replace('",', '"},')
                            lines[i] = lines[i].replace('"]', '"}]')
                        }
                    }

                }

                for (let i = 0; i < lines.length; i++) {
                    result += lines[i]
                }
                result += '}'
                fs.writeFileSync('output/' + filename.replace(".md", ".json"), result)
                console.log('File added/updated: ' + filename.replace(".md", ".json"))
            })
        }

    })

})