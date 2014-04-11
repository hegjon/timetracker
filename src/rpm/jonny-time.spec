Name: jonny-time
Version: 0.1
Release: 1%{?dist}
Summary: Jonny time tracker

License: ASL 2.0
URL: http://jonnyware.com

Source0: %{name}.tar.gz
BuildArch: noarch

BuildRequires: java-devel >= 1:1.5.0
BuildRequires: maven-local
Requires:      java-headless >= 1:1.5.0

BuildRequires: snakeyaml
BuildRequires: joda-time
BuildRequires: apache-commons-lang3
BuildRequires: apache-commons-cli
BuildRequires: junit >= 4
BuildRequires: hamcrest
Requires:      apache-commons-codec

BuildRequires: python-docutils

%description
Hmm..

%package javadoc
Summary: Javadoc for %{name}

%description javadoc
%{summary}.

%prep

%setup -c

%build
%mvn_build
rst2man < src/doc/jtime.rst | gzip > target/jtime.1.gz

%install
%mvn_install
install -d %{buildroot}%{_mandir}/man1
install target/jtime.1.gz %{buildroot}%{_mandir}/man1/jtime.1.gz

%jpackage_script com.jonnyware.timetracker.Main "-Xint" "" jonny-time:joda-time:apache-commons-lang3:snakeyaml:apache-commons-cli:apache-commons-codec jtime true

%files -f .mfiles
%{_bindir}/jtime
%doc LICENSE.txt
%doc TODO.txt
%doc %{_mandir}/man1/jtime.1.gz

%files javadoc -f .mfiles-javadoc
%doc LICENSE.txt

%changelog
* Tue Mar 25 2014 Jonny Heggheim <hegjon@gmail.com> - 1.0-1
- Initial packaging
