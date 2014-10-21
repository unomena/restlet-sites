#! /usr/bin/env python
# -*- coding: utf-8 -*-

import argparse
import os
import yaml
import re
import sys


def main():
    parser = argparse.ArgumentParser(description='Create ToC (toc.yml) from a given path. '
                                                 'All files & folders must begin with xx_name.')
    parser.add_argument('--path', '-p',  type=str, help='Path to scan. If not provided takes current path.')
    parser.add_argument('--markdown', '-m',  action='store_true', help='If provided, return a markdown file.')

    args = parser.parse_args()
    path = args.path
    markdown = args.markdown

    if path is None:
        path = os.getcwd()

    document = []
    scan_dir(path, document, '', '')

    obj = {'toc': document}

    if not markdown:
        print yaml.dump(obj, default_flow_style=False)
    else:
        convert_to_markdown(document)


def scan_dir(path, document, path_to_display, link_url):

    elements = os.listdir(path)
    elements.sort()

    for element in elements:

        new_elem = os.path.join(path, element)

        if os.path.isdir(new_elem):

            # Check that the directory has a matching .yml file
            if os.path.exists(new_elem + '.yml'):
                add_dir(new_elem, document, new_elem + '.yml', path_to_display, link_url)

        elif os.path.isfile(new_elem):

            # Find markdown files with a corresponding .yml file
            regexp = re.match(r'(.*)\.md', new_elem)
            if regexp and os.path.exists(regexp.group(1) + '.yml'):
                add_markdown_file(new_elem, document, regexp.group(1) + '.yml', path_to_display, link_url)


def add_dir(path, document, yml_path, path_to_display, link_url):

    to_add = yaml.load(open(yml_path, 'r'))
    document.append(to_add)

    basename = os.path.basename(path)
    to_add['dir'] = basename

    try:
        to_add['id'] = re.match(r'[0-9]*_(.*)', basename).group(1)
    except Exception:
        sys.stderr.write('Error with folder %s. Wrong regexp.' % path)
        sys.exit(1)

    items_next = []
    to_add['items'] = items_next
    scan_dir(path, items_next, path_to_display + '/' + to_add['id'], link_url + '/' + to_add['dir'])


def add_markdown_file(path, document, yml_path, path_to_display, link_url):

    to_add = yaml.load(open(yml_path, 'r'))
    if not to_add:
        sys.stderr.write('Error with file %s' % yml_path)
        sys.exit(1)

    document.append(to_add)

    basename = os.path.basename(path)
    to_add['file'] = basename
    to_add['link_url'] = link_url + '/' + basename

    try:
        to_add['id'] = re.match(r'[0-9]*_(.*)\.md', basename).group(1)
    except Exception:
        sys.stderr.write('Error with file %s. Wrong regexp.' % path)
        sys.exit(1)

    to_add['url'] = path_to_display + '/' + to_add['id']


def convert_to_markdown(document):
    indentation = ''
    for line in document:
        scan_line(line, indentation)


def scan_line(line, indentation):
    if 'dir' in line.keys():
        # Is directory
        print '%s* %s' % (indentation, line['title'])
        for item in line['items']:
            scan_line(item, indentation + '  ')
    elif 'file' in line.keys():
        # Is file
        print '%s* [%s](%s)' % (indentation, line['title'], line['link_url'])

if __name__ == "__main__":
    main()