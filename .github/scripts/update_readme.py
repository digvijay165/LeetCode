"""Rebuild README.md from the problem folders that LeetHub pushes.

Runs in GitHub Actions on every push. Uses only the Python standard library.
"""

import html
import json
import os
import re

# ---- Edit these to personalise the README --------------------------------
GITHUB_USER = "digvijay165"
LEETCODE_USER = "digvijay165"  # https://leetcode.com/u/<this>/
AUTHOR_NAME = "Digvijay Singh"
# ---------------------------------------------------------------------------

ROOT = os.path.abspath(os.path.join(os.path.dirname(__file__), "..", ".."))
README = os.path.join(ROOT, "README.md")
TOPICS_FILE = os.path.join(ROOT, "topics.json")
TOPICS_START = "<!---LeetCode Topics Start-->"
TOPICS_END = "<!---LeetCode Topics End-->"

LANGUAGES = {
    ".cpp": "C++", ".c": "C", ".java": "Java", ".py": "Python",
    ".js": "JavaScript", ".ts": "TypeScript", ".go": "Go", ".rs": "Rust",
    ".kt": "Kotlin", ".swift": "Swift", ".cs": "C#", ".rb": "Ruby",
    ".php": "PHP", ".scala": "Scala", ".dart": "Dart", ".sql": "SQL",
    ".sh": "Bash",
}
DIFFICULTY_ICON = {"Easy": "🟢", "Medium": "🟡", "Hard": "🔴"}


def read(path):
    with open(path, encoding="utf-8") as f:
        return f.read()


def topics_block(readme_text):
    """Return LeetHub's topics section (markers included), or "" if absent."""
    start = readme_text.find(TOPICS_START)
    end = readme_text.find(TOPICS_END)
    if start == -1 or end == -1:
        return ""
    return readme_text[start:end + len(TOPICS_END)]


def load_topics(block):
    """Merge topics LeetHub just added to the README into topics.json.

    LeetHub only lists its newest problems in that section, so topics.json
    keeps the full history and the section itself is left out of the README.
    """
    saved = json.loads(read(TOPICS_FILE)) if os.path.exists(TOPICS_FILE) else {}
    for folder, topics in topics_by_problem(block).items():
        known = saved.setdefault(folder, [])
        known.extend(t for t in topics if t not in known)
    with open(TOPICS_FILE, "w", encoding="utf-8", newline="\n") as f:
        json.dump(dict(sorted(saved.items())), f, indent=2, ensure_ascii=False)
        f.write("\n")
    return saved


def topics_by_problem(block):
    """Map folder name -> [topics], parsed from LeetHub's topic tables."""
    result, topic = {}, None
    for line in block.splitlines():
        heading = re.match(r"^##\s+(.+)$", line)
        if heading:
            topic = heading.group(1).strip()
            continue
        for folder in re.findall(r"\[(\d{4}-[^\]]+)\]", line):
            if topic:
                result.setdefault(folder, []).append(topic)
    return result


def load_problems():
    problems = []
    for folder in sorted(os.listdir(ROOT)):
        path = os.path.join(ROOT, folder)
        match = re.match(r"^(\d+)-", folder)
        if not match or not os.path.isdir(path):
            continue

        title = folder[len(match.group(0)):].replace("-", " ").title()
        url = "https://leetcode.com/problems/" + folder[len(match.group(0)):]
        difficulty = "Unknown"
        problem_readme = os.path.join(path, "README.md")
        if os.path.exists(problem_readme):
            head = read(problem_readme)[:600]
            link = re.search(r'<a href="([^"]+)">\s*(?:\d+\.\s*)?([^<]+)</a>', head)
            if link:
                url, title = link.group(1), html.unescape(link.group(2)).strip()
            level = re.search(r"<h3>\s*(Easy|Medium|Hard)\s*</h3>", head)
            if level:
                difficulty = level.group(1)

        languages = sorted({
            LANGUAGES[ext] for ext in
            (os.path.splitext(name)[1].lower() for name in os.listdir(path))
            if ext in LANGUAGES
        })
        problems.append({
            "folder": folder,
            "number": int(match.group(1)),
            "title": title,
            "url": url,
            "difficulty": difficulty,
            "languages": languages,
        })
    return sorted(problems, key=lambda p: p["number"])


def badge(label, value, color, logo=""):
    label = label.replace("-", "--").replace(" ", "_")
    value = str(value).replace("-", "--").replace(" ", "_")
    extra = f"&logo={logo}&logoColor=white" if logo else ""
    return f"![{label}](https://img.shields.io/badge/{label}-{value}-{color}?style=flat-square{extra})"


def build(problems, tags):
    count = {d: sum(p["difficulty"] == d for p in problems) for d in DIFFICULTY_ICON}
    total = len(problems)
    lang_count = {}
    for p in problems:
        for lang in p["languages"]:
            lang_count[lang] = lang_count.get(lang, 0) + 1
    topic_count = {}
    for p in problems:
        for t in tags.get(p["folder"], []):
            topic_count[t] = topic_count.get(t, 0) + 1

    profile = f"https://leetcode.com/u/{LEETCODE_USER}/"
    lang_names = " and ".join(f"**{l}**" for l in sorted(lang_count)) or "multiple languages"

    rows = []
    for p in problems:
        icon = DIFFICULTY_ICON.get(p["difficulty"], "⚪")
        topics = ", ".join(f"`{t}`" for t in tags.get(p["folder"], [])) or "—"
        rows.append(
            f"| {p['number']} | [{p['title']}]({p['url']}) | {icon} {p['difficulty']} "
            f"| {', '.join(p['languages']) or '—'} | {topics} | [View](./{p['folder']}) |"
        )

    lang_rows = "\n".join(
        f"| {lang} | {n} |" for lang, n in sorted(lang_count.items(), key=lambda x: -x[1])
    )
    topic_line = " · ".join(
        f"`{t}` ({n})" for t, n in sorted(topic_count.items(), key=lambda x: (-x[1], x[0]))
    ) or "_Topics appear here as LeetHub tags new problems._"

    return f"""<div align="center">

# 🧠 LeetCode Solutions

### *Sharpening problem-solving skills, one problem at a time.*

My collection of LeetCode solutions in {lang_names}, synced automatically from LeetCode with [LeetHub v2](https://github.com/arunbhardwaj/LeetHub-2.0).

[![LeetCode Profile](https://img.shields.io/badge/🚀_LeetCode-View_My_Profile-FFA116?style=for-the-badge&logo=leetcode&logoColor=white)]({profile})

{badge("Solved", total, "blue")}
{badge("Easy", count["Easy"], "00b8a3")}
{badge("Medium", count["Medium"], "ffc01e")}
{badge("Hard", count["Hard"], "ff375f")}

**[🌐 LeetCode Profile]({profile})** · **[👤 GitHub](https://github.com/{GITHUB_USER})**

</div>

---

## 📖 About

This repository tracks my journey through **Data Structures & Algorithms** on LeetCode. Each problem has its own folder with:

- 📄 `README.md`: the full problem statement, examples, and constraints
- 💻 Solution file: my accepted solution

New accepted submissions are pushed here automatically by **LeetHub**, and this README **updates itself** through GitHub Actions.

---

## 📊 Progress

| Difficulty | Solved |
| :---: | :---: |
| 🟢 Easy | {count["Easy"]} |
| 🟡 Medium | {count["Medium"]} |
| 🔴 Hard | {count["Hard"]} |
| **Total** | **{total}** |

| Language | Solutions |
| :---: | :---: |
{lang_rows}

---

## 📚 Solutions

| # | Problem | Difficulty | Language | Topics | Solution |
| :---: | --- | :---: | :---: | --- | :---: |
{chr(10).join(rows)}

---

## 🧩 Topics Covered

{topic_line}

---

## ⚙️ How It Works

1. Solve a problem on **LeetCode**
2. When it's accepted, **LeetHub** pushes the solution to this repo
3. A **GitHub Action** rebuilds this README with the latest stats and solutions table

---

## 🤝 Connect

If you find these solutions helpful, feel free to ⭐ the repo or suggest better approaches through issues!

- **LeetCode:** [@{LEETCODE_USER}]({profile})
- **GitHub:** [@{GITHUB_USER}](https://github.com/{GITHUB_USER})

---

<div align="center">

⭐ **Consistency beats intensity. Keep coding!** ⭐

Made with 💻 by [{AUTHOR_NAME}](https://github.com/{GITHUB_USER})

</div>
"""


def main():
    current = read(README) if os.path.exists(README) else ""
    tags = load_topics(topics_block(current))
    new = build(load_problems(), tags)
    if new != current:
        with open(README, "w", encoding="utf-8", newline="\n") as f:
            f.write(new)
        print("README.md updated")
    else:
        print("README.md already up to date")


if __name__ == "__main__":
    main()
