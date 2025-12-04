#!/bin/bash

#    1400 | SHA2-256                                                   | Raw Hash
#   17400 | SHA3-256                                                   | Raw Hash
#   11700 | GOST R 34.11-2012 (Streebog) 256-bit, big-endian           | Raw Hash
#    6900 | GOST R 34.11-94                                            | Raw Hash
#   17800 | Keccak-256                                                 | Raw Hash
#    1470 | sha256(utf16le($pass))                                     | Raw Hash
#   20800 | sha256(md5($pass))                                         | Raw Hash salted and/or iterated
#   21400 | sha256(sha256_bin($pass))                                  | Raw Hash salted and/or iterated

#     900 | MD4                                                        | Raw Hash
#       0 | MD5                                                        | Raw Hash
#      70 | md5(utf16le($pass))                                        | Raw Hash
#    2600 | md5(md5($pass))                                            | Raw Hash salted and/or iterated
#    3500 | md5(md5(md5($pass)))                                       | Raw Hash salted and/or iterated
#    4400 | md5(sha1($pass))                                           | Raw Hash salted and/or iterated
#   20900 | md5(sha1($pass).md5($pass).sha1($pass))                    | Raw Hash salted and/or iterated
#    4300 | md5(strtoupper(md5($pass)))                                | Raw Hash salted and/or iterated
#    1000 | NTLM                                                       | Operating System
#    9900 | Radmin2                                                    | Operating System
#    8600 | Lotus Notes/Domino 5                                       | Enterprise Application Software (EAS)


dictionary_repos=(
    "https://github.com/danielmiessler/SecLists.git"
    "https://github.com/00xBAD/kali-wordlists.git"
    "https://github.com/00xZEROx00/kali-wordlists.git"
)

dictionary_dirs=(
    "SecLists"
    "kali-wordlists-00xBAD"
    "kali-wordlists-00xZEROx00"
)

for repo_index in "${!dictionary_repos[@]}"; do
    repo="${dictionary_repos[$repo_index]}"
    dir="${dictionary_dirs[$repo_index]}"

    if [ ! -d "$dir" ]; then
        git clone "$repo" "$dir"
    else
        echo "Directory $dir already exists. Skipping clone."
    fi
done

hash256_types=(
    "1400"
    "17400"
    "11700"
    "6900"
    "17800"
    "1470"
    "20800"
    "21400"
)
# 1400, 1470, 6900, 11700, 17400, 17800, 20800, 21400

hash_types=(
    "500"
    # "900"
    # "0"
    # "70"
    # "2600"
    # "3500"
    # "4400"
    # "20900"
    # "4300"
    # "1000"
    # "9900"
    # "8600"
)
# 0, 70, 900, 1000, 2600, 3500, 4300, 4400, 8600, 9900, 20900
hash_file="hash.txt"

# check hash type of a given hash:
hashcat --identify "$hash_file"

for hash_type in "${hash_types[@]}"; do
    hashcat -m "$hash_type" -a 0 "$hash_file" SecLists/**/*.txt
    hashcat -m "$hash_type" -a 0 "$hash_file" kali-wordlists-00xZEROx00/**/*.txt
    hashcat -m "$hash_type" -a 0 "$hash_file" kali-wordlists-00xBAD/**/*.txt

    # hashcat -m "$hash_type" -a 0 "$hash_file" kali-wordlists-00xBAD/**/*.lst
    # hashcat -m "$hash_type" -a 0 "$hash_file" kali-wordlists-00xZEROx00/**/*.lst
    # hashcat -m "$hash_type" -a 0 ../../hash.txt ../../kali-wordlists/rockyou.txt
done