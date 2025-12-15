# Level 1 (Windows) (BLUE TEAM)

The successful exploits were:

1.  **MS17-010 EternalBlue Vulnerability** (Exploited via port **445/tcp**).
2.  **ElasticSearch MVEL Script RCE** (Exploited via port **9200/tcp**).

---

## 1. Technical Fixes (Prevent the Vulnerability)

The underlying cause for both successful exploits is the use of severely outdated and unpatched software.

### A. MS17-010 EternalBlue Vulnerability (Port 445 - SMB)

The target is running **Windows Server 2008 R2 Standard 7601 Service Pack 1**, which was vulnerable to the **MS17-010** vulnerability, commonly known as **EternalBlue**. This flaw allows unauthenticated attackers to execute arbitrary code with kernel privileges by sending specially crafted packets to the Microsoft Server Message Block 1.0 (SMBv1) server.

* **The Fix:**
    * **Patching:** The definitive fix is to apply the **security update MS17-010**. For Windows Server 2008 R2, this means installing the rollup KB4012212 or later.
    * **Decommission SMBv1:** **Disable the SMBv1 protocol** on the server entirely, as it is old, insecure, and not required for modern Windows communication. This can be done via PowerShell or the registry.
    * **Firewall:** Restrict access to ports **445/tcp** and **139/tcp** to only trusted internal networks or specific administrative hosts. **Block it entirely from the public internet.**

### B. ElasticSearch MVEL Script RCE (Port 9200)

The Nmap scan identified the service on port 9200 as **ElasticSearch version 1.1.1**. This version is vulnerable to the **Dynamic Script Arbitrary Java Execution** flaw (CVE-2014-3120) via the MVEL scripting engine, which you successfully exploited.

* **The Fix:**
    * **Upgrade/Replace:** **Immediately upgrade** ElasticSearch to a modern, supported version (ideally 6.x, 7.x, or 8.x) where dynamic scripting is disabled by default or uses a secure language like Painless. ElasticSearch 1.1.1 is ancient and has numerous other critical flaws.
    * **Disable Dynamic Scripting:** If an immediate upgrade isn't possible, configure ElasticSearch to **disable dynamic scripting** or enforce a sandboxed scripting environment.
    * **Network Restriction:** Place ElasticSearch behind a firewall or reverse proxy and **bind it only to the localhost interface (127.0.0.1)**. Public access to the raw ElasticSearch port (9200) should be strictly forbidden; only applications or proxies should interact with it.

---

## 2. Managerial Answers (Security Rules & Policies)

These policies are necessary to prevent a recurrence of these critical failures.

### A. **End-of-Life (EOL) Policy and System Refresh**

* **Rule:** Establish a clear policy that mandates the **decommissioning or migration** of all servers running operating systems or major applications that have reached their **vendor's EOL date**.
* **Action:** **Windows Server 2008 R2** is EOL (Extended Support ended in January 2020). Running this OS is an unacceptable security risk. The target must be replaced with a modern, fully supported OS like Windows Server 2019 or 2022.

### B. **Proactive Patch Management Policy**

* **Rule:** Implement a structured process to monitor vendor security advisories (e.g., Microsoft Security Response Center, Elastic) and ensure **critical patches are deployed immediately** (e.g., within 24-48 hours) upon release.
* **Action:** Utilize tools like **Windows Server Update Services (WSUS)** or Microsoft Endpoint Configuration Manager (MECM) to automate patch deployment and enforce compliance across the server fleet.

### C. **Default Credentials and Hardening Policy**

* **Rule:** Enforce a policy that **prohibits the use of blank or default passwords** for any service, especially databases.
* **Action:** The finding of a blank password for the **`root`** user on **MySQL (Port 3306)** is a major configuration failure (Attempt 3). All default accounts must be removed, renamed, or assigned strong, complex, unique passwords immediately. Implement regular credential auditing.
